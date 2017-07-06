// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include <stdlib.h>
#include <errno.h>

#define DEFAULT_MEMORY 512 * 1024 * 1024 // 512MB
#define INDEX_HASH_WIDTH 8
#define INDEX_POSITION_WIDTH 6
#define INDEX_ENTRY_WIDTH (INDEX_HASH_WIDTH + INDEX_POSITION_WIDTH)
#define INSERTIONSORT_THRESHOLD 16

struct IndexEntry {
    unsigned char hash[INDEX_HASH_WIDTH]; // First 64 bits of the hash
    unsigned char position[INDEX_POSITION_WIDTH]; // Position of word in dictionary (48-bit little endian integer)
};

void printUsage();
int sortFile(FILE *file, struct IndexEntry *sortBuffer, int64_t bufcount);
void quickSortFile(FILE* file, int64_t lowerIdx, int64_t upperIdx, struct IndexEntry *sortBuffer, int64_t bufcount);
int64_t partitionFile(FILE* file, int64_t lowerIdx, int64_t upperIdx);
void quickSortMemory(struct IndexEntry *sortBuffer, int64_t lowerIdx, int64_t upperIdx);
int64_t partitionMemory(struct IndexEntry *sortBuffer, int64_t lowerIdx, int64_t upperIdx);
void insertionSortMemory(struct IndexEntry *sortBuffer, int64_t lowerIdx, int64_t upperIdx);

int hashcmp(const unsigned char hashA[INDEX_HASH_WIDTH], const unsigned char hashB[INDEX_HASH_WIDTH]);
void freadIndexEntryAt(FILE* file, int64_t index, struct IndexEntry* out);
void fwriteIndexEntryAt(FILE* file, int64_t index, struct IndexEntry* in);
void loadFileToBuffer(FILE* file, struct IndexEntry* buffer, int64_t lowerIdx, int64_t upperIdx, int64_t bufsize);
void writeBufferToFile(FILE* file, struct IndexEntry* buffer, int64_t lowerIdx, int64_t writeCount);


int main(int argc, char** argv)
{
    int i;
    int64_t bufsize = DEFAULT_MEMORY, bufcount;
    FILE *index = NULL;
    struct IndexEntry *sortBuffer;

    if(argc < 2)
    {
        printUsage();
        return 1;
    }

    for(i = 1; i < argc; i++)
    {
        if(strcmp("-r", argv[i]) == 0 && i + 1 < argc)
            bufsize = (int64_t)atoi(argv[++i]) * 1024 * 1024;
        else{
            index = fopen(argv[i], "r+b");
            printf("Sorting file %s ... ", argv[i]);
            fflush(stdout);
        }
    }

    if(bufsize <= 0)
    {
        printf("\nInvalid buffer size (%d).\n", bufsize);
        printUsage();
        return 1;
    }

    if(index == NULL)
    {
        printf("\nCouldn't open index file for reading/writing.\n");
        printUsage();
        return 1;
    }

    // Adjust bufsize to a multiple of the size of an IndexEntry
    bufcount = bufsize / sizeof(struct IndexEntry);
    bufsize = bufcount * sizeof(struct IndexEntry);
    sortBuffer = malloc(bufsize);

    if(sortBuffer == NULL)
    {
        printf("\nCannot allocate buffer (%d bytes).\n", bufsize);
        printUsage();
        return 1;
    }

    if(sortFile(index, sortBuffer, bufcount))
    {
        printf("done!\n");
    }
    else
    {
        printf("\nThere was a problem sorting the file.\n");
    }

    if(sortBuffer != NULL)
        free(sortBuffer);
    if(index != NULL)
        fclose(index);

    return 0;
}

void printUsage()
{
    printf( "Usage: sortidx [OPTIONS] <INDEX>\n\n"
            "Options:\n"
            "-r n      'n' is the sort buffer size (memory) in megabytes\n"
          );
}

/*
 * Sort index file.
 * file - Index file.
 * sortBuffer - RAM buffer used to speed up sorting.
 * bufcount - Size of buffer (number of elements).
 */
int sortFile(FILE *file, struct IndexEntry *sortBuffer, int64_t bufcount)
{
    fseek(file, 0L, SEEK_END);
    int64_t size = ftell(file);
    if(size % INDEX_ENTRY_WIDTH != 0)
        return 0;
    int64_t numEntries = size / INDEX_ENTRY_WIDTH;
    quickSortFile(file, 0, numEntries - 1, sortBuffer, bufcount);
    return 1;
}


/*
 * File QuickSort.
 * file - Index file.
 * lowerIdx - Lower index of range to sort.
 * upperIdx - Upper index of range to sort.
 * sortBuffer - RAM buffer. Will switch to in-memory sort when range can fit in this buffer.
 * bufcount - Size of buffer (number of elements).
 */
void quickSortFile(FILE* file, int64_t lowerIdx, int64_t upperIdx, struct IndexEntry *sortBuffer, int64_t bufcount)
{
    int64_t size = upperIdx - lowerIdx + 1;
    if(size >= 2)
    {
        if(size <= bufcount)
        {
            loadFileToBuffer(file, sortBuffer, lowerIdx, upperIdx, bufcount);
            quickSortMemory(sortBuffer, 0, size-1);
            writeBufferToFile(file, sortBuffer, lowerIdx, size);
        }
        else
        {
            int64_t newPivot = partitionFile(file, lowerIdx, upperIdx);
            /* TODO: Sort the smallest partition first */
            quickSortFile(file, lowerIdx, newPivot - 1, sortBuffer, bufcount);
            quickSortFile(file, newPivot + 1, upperIdx, sortBuffer, bufcount);
        }
    }
}

/*
 * QuickSort Partition Step - File.
 * file - Index file.
 * lowerIdx - Lower index of range to partition.
 * upperIdx - Upper index of range to partition (inclusive).
 * returns: Pivot index.
 */
int64_t partitionFile(FILE* file, int64_t lowerIdx, int64_t upperIdx)
{
    int64_t pivotIdx = lowerIdx + (upperIdx-lowerIdx)/2; /* TODO: Median method. But: This is good enough since the data is either sorted or randomly distributed. */
    struct IndexEntry pivot;
    freadIndexEntryAt(file, pivotIdx, &pivot);
    /* Place pivot at the end */
    struct IndexEntry tmp;
    freadIndexEntryAt(file, upperIdx, &tmp);
    fwriteIndexEntryAt(file, upperIdx, &pivot);
    fwriteIndexEntryAt(file, pivotIdx, &tmp);

    struct IndexEntry tmp2;

    int64_t storeIndex = lowerIdx;
    int64_t i;
    for(i = lowerIdx; i < upperIdx; i++) /* Should be <, put <= to test */
    {
         freadIndexEntryAt(file, i, &tmp);
         if(hashcmp(tmp.hash, pivot.hash) < 0)
         {
            /* Swap i-th and storeIndex */
            freadIndexEntryAt(file, storeIndex, &tmp2);
            fwriteIndexEntryAt(file, storeIndex, &tmp);
            fwriteIndexEntryAt(file, i, &tmp2);
            storeIndex++;
         }
    }

    freadIndexEntryAt(file, storeIndex, &tmp2);
    fwriteIndexEntryAt(file, storeIndex, &pivot);
    fwriteIndexEntryAt(file, upperIdx, &tmp2);
    return storeIndex;
}

/*
 * In-Memory QuickSort.
 * sortBuffer - IndexEntries to sort.
 * lowerIdx - Lower index of the range to sort.
 * upperIdx - Upper index of the range to sort (inclusive).
 */
void quickSortMemory(struct IndexEntry *sortBuffer, int64_t lowerIdx, int64_t upperIdx)
{
    int64_t size = upperIdx - lowerIdx + 1;
    if(size >= 2)
    {
        if(size <= INSERTIONSORT_THRESHOLD)
        {
            insertionSortMemory(sortBuffer, lowerIdx, upperIdx);
        }
        else
        {
            int64_t newPivot = partitionMemory(sortBuffer, lowerIdx, upperIdx);
            /* TODO: Sort the smallest partition first */
            quickSortMemory(sortBuffer, lowerIdx, newPivot - 1);
            quickSortMemory(sortBuffer, newPivot + 1, upperIdx);
        }
    }
}

/*
 * QuickSort Partition Step - RAM.
 * sortBuffer - Index entries to sort.
 * lowerIdx - Lower index of range to partition.
 * upperIdx - Upper index of range to partition (inclusive).
 * returns: Pivot index.
 */
int64_t partitionMemory(struct IndexEntry *sortBuffer, int64_t lowerIdx, int64_t upperIdx)
{
    int64_t pivotIndex = lowerIdx + (upperIdx-lowerIdx)/2; /* TODO: Median method. But: This is good enough since the data is either sorted or randomly distributed. */
    struct IndexEntry pivotValue = sortBuffer[pivotIndex];
    sortBuffer[pivotIndex] = sortBuffer[upperIdx];
    sortBuffer[upperIdx] = pivotValue;

    int64_t storeIndex = lowerIdx;
    int64_t i;
    struct IndexEntry tmp;
    for(i = lowerIdx; i < upperIdx; i++)
    {
        if(hashcmp(sortBuffer[i].hash, pivotValue.hash) < 0)
        {
            tmp = sortBuffer[i];
            sortBuffer[i] = sortBuffer[storeIndex];
            sortBuffer[storeIndex] = tmp;
            storeIndex++;
        }
    }

    tmp = sortBuffer[storeIndex];
    sortBuffer[storeIndex] = pivotValue;
    sortBuffer[upperIdx] = tmp;
    return storeIndex;
}

void insertionSortMemory(struct IndexEntry *sortBuffer, int64_t lowerIdx, int64_t upperIdx)
{
    int64_t size = upperIdx - lowerIdx + 1;
    struct IndexEntry key;
    int64_t j;
    int64_t i;
    for(j = 0; j < size; j++)
    {
        key = sortBuffer[j + lowerIdx];
        i = j - 1;
        while( i >= 0 && hashcmp(sortBuffer[i + lowerIdx].hash, key.hash) > 0)
        {
            sortBuffer[i + 1 + lowerIdx] = sortBuffer[i + lowerIdx];
            i--;
        }
        sortBuffer[i + 1 + lowerIdx] = key;
    }
}

/*
 * Compares two INDEX_HASH_WIDTH-char arrays.
 * Returns 1 if the first argument is greater than the second.
 * Returns -1 if the first argument is less than the second.
 * Returns 0 if both are equal.
 */
int hashcmp(const unsigned char hashA[INDEX_HASH_WIDTH], const unsigned char hashB[INDEX_HASH_WIDTH])
{
    int i = 0;
    for(i = 0; i < INDEX_HASH_WIDTH; i++)
    {
        if(hashA[i] > hashB[i])
            return 1;
        else if(hashA[i] < hashB[i])
            return -1;
    }

    return 0;
}

void loadFileToBuffer(FILE* file, struct IndexEntry* buffer, int64_t lowerIdx, int64_t upperIdx, int64_t bufsize)
{
    int64_t i, s;
    for(i = lowerIdx, s = 0; i <= upperIdx && s < bufsize; i++, s++)
    {
        freadIndexEntryAt(file, i, buffer + s);
    }
}

void writeBufferToFile(FILE* file, struct IndexEntry* buffer, int64_t lowerIdx, int64_t writeCount)
{
    int64_t i;
    for(i = 0; i < writeCount; i++)
    {
        fwriteIndexEntryAt(file, lowerIdx + i, buffer + i);
    }
}

void freadIndexEntryAt(FILE* file, int64_t index, struct IndexEntry* out)
{
    fseek(file, index * INDEX_ENTRY_WIDTH, SEEK_SET);
    fread(out->hash, sizeof(unsigned char), INDEX_HASH_WIDTH, file);
    fread(out->position, sizeof(unsigned char), INDEX_POSITION_WIDTH, file);
}

void fwriteIndexEntryAt(FILE* file, int64_t index, struct IndexEntry* in)
{
    fseek(file, index * INDEX_ENTRY_WIDTH, SEEK_SET);
    fwrite(in->hash, sizeof(unsigned char), INDEX_HASH_WIDTH, file);
    fwrite(in->position, sizeof(unsigned char), INDEX_POSITION_WIDTH, file);
}
