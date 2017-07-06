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
#include <inttypes.h>


#define INDEX_HASH_WIDTH 8
#define INDEX_POSITION_WIDTH 6
#define INDEX_ENTRY_WIDTH (INDEX_HASH_WIDTH + INDEX_POSITION_WIDTH)

struct IndexEntry {
    unsigned char hash[INDEX_HASH_WIDTH]; // First 64 bits of the hash
    unsigned char position[INDEX_POSITION_WIDTH]; // Position of word in dictionary (48-bit little endian integer)
};


void freadIndexEntryAt(FILE* file, int64_t index, struct IndexEntry* out)
{
    fseek(file, index * INDEX_ENTRY_WIDTH, SEEK_SET);
    fread(out->hash, sizeof(unsigned char), INDEX_HASH_WIDTH, file);
    fread(out->position, sizeof(unsigned char), INDEX_POSITION_WIDTH, file);
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


int main(int argc, char **argv)
{
    struct IndexEntry current, max;
    FILE* file = fopen(argv[1], "r+b");

    if(file == NULL)
    {
        printf("File does not exist.\n");
        return 3;
    }

    fseek(file, 0L, SEEK_END);
    int64_t size = ftell(file);
    if(size % INDEX_ENTRY_WIDTH != 0)
    {
        printf("Invalid index file!\n");
        return 1;
    }
    int64_t numEntries = size / INDEX_ENTRY_WIDTH;

    int64_t i;

    for(i = 0; i < numEntries; i++)
    {
        freadIndexEntryAt(file, i, &current);
        if(hashcmp(current.hash, max.hash) < 0) // Current is less than max
        {
            printf("NOT SORTED!!!!\n");
            return 2;
        }
        max = current;
        if(i % 10000000 == 0)
        {
            printf("%d...\n", i);
        }
    }

    printf("ALL SORTED!\n");
}
