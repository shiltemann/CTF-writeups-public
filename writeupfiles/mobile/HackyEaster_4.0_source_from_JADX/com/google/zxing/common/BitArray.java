package com.google.zxing.common;

import org.apache.http.message.TokenParser;

public final class BitArray {
    private int[] bits;
    private int size;

    public BitArray() {
        this.size = 0;
        this.bits = new int[1];
    }

    public BitArray(int size) {
        this.size = size;
        this.bits = makeArray(size);
    }

    public int getSize() {
        return this.size;
    }

    public int getSizeInBytes() {
        return (this.size + 7) >> 3;
    }

    private void ensureCapacity(int size) {
        if (size > (this.bits.length << 5)) {
            int[] newBits = makeArray(size);
            System.arraycopy(this.bits, 0, newBits, 0, this.bits.length);
            this.bits = newBits;
        }
    }

    public boolean get(int i) {
        return (this.bits[i >> 5] & (1 << (i & 31))) != 0;
    }

    public void set(int i) {
        int[] iArr = this.bits;
        int i2 = i >> 5;
        iArr[i2] = iArr[i2] | (1 << (i & 31));
    }

    public void flip(int i) {
        int[] iArr = this.bits;
        int i2 = i >> 5;
        iArr[i2] = iArr[i2] ^ (1 << (i & 31));
    }

    public int getNextSet(int from) {
        if (from >= this.size) {
            return this.size;
        }
        int bitsOffset = from >> 5;
        int currentBits = this.bits[bitsOffset] & (((1 << (from & 31)) - 1) ^ -1);
        while (currentBits == 0) {
            bitsOffset++;
            if (bitsOffset == this.bits.length) {
                return this.size;
            }
            currentBits = this.bits[bitsOffset];
        }
        int result = (bitsOffset << 5) + Integer.numberOfTrailingZeros(currentBits);
        return result > this.size ? this.size : result;
    }

    public int getNextUnset(int from) {
        if (from >= this.size) {
            return this.size;
        }
        int bitsOffset = from >> 5;
        int currentBits = (this.bits[bitsOffset] ^ -1) & (((1 << (from & 31)) - 1) ^ -1);
        while (currentBits == 0) {
            bitsOffset++;
            if (bitsOffset == this.bits.length) {
                return this.size;
            }
            currentBits = this.bits[bitsOffset] ^ -1;
        }
        int result = (bitsOffset << 5) + Integer.numberOfTrailingZeros(currentBits);
        return result > this.size ? this.size : result;
    }

    public void setBulk(int i, int newBits) {
        this.bits[i >> 5] = newBits;
    }

    public void setRange(int start, int end) {
        if (end < start) {
            throw new IllegalArgumentException();
        } else if (end != start) {
            end--;
            int firstInt = start >> 5;
            int lastInt = end >> 5;
            int i = firstInt;
            while (i <= lastInt) {
                int mask;
                int firstBit = i > firstInt ? 0 : start & 31;
                int lastBit = i < lastInt ? 31 : end & 31;
                if (firstBit == 0 && lastBit == 31) {
                    mask = -1;
                } else {
                    mask = 0;
                    for (int j = firstBit; j <= lastBit; j++) {
                        mask |= 1 << j;
                    }
                }
                int[] iArr = this.bits;
                iArr[i] = iArr[i] | mask;
                i++;
            }
        }
    }

    public void clear() {
        int max = this.bits.length;
        for (int i = 0; i < max; i++) {
            this.bits[i] = 0;
        }
    }

    public boolean isRange(int start, int end, boolean value) {
        if (end < start) {
            throw new IllegalArgumentException();
        } else if (end == start) {
            return true;
        } else {
            end--;
            int firstInt = start >> 5;
            int lastInt = end >> 5;
            int i = firstInt;
            while (i <= lastInt) {
                int mask;
                int firstBit = i > firstInt ? 0 : start & 31;
                int lastBit = i < lastInt ? 31 : end & 31;
                if (firstBit == 0 && lastBit == 31) {
                    mask = -1;
                } else {
                    mask = 0;
                    for (int j = firstBit; j <= lastBit; j++) {
                        mask |= 1 << j;
                    }
                }
                int i2 = this.bits[i] & mask;
                if (!value) {
                    mask = 0;
                }
                if (i2 != mask) {
                    return false;
                }
                i++;
            }
            return true;
        }
    }

    public void appendBit(boolean bit) {
        ensureCapacity(this.size + 1);
        if (bit) {
            int[] iArr = this.bits;
            int i = this.size >> 5;
            iArr[i] = iArr[i] | (1 << (this.size & 31));
        }
        this.size++;
    }

    public void appendBits(int value, int numBits) {
        if (numBits < 0 || numBits > 32) {
            throw new IllegalArgumentException("Num bits must be between 0 and 32");
        }
        ensureCapacity(this.size + numBits);
        for (int numBitsLeft = numBits; numBitsLeft > 0; numBitsLeft--) {
            appendBit(((value >> (numBitsLeft + -1)) & 1) == 1);
        }
    }

    public void appendBitArray(BitArray other) {
        int otherSize = other.size;
        ensureCapacity(this.size + otherSize);
        for (int i = 0; i < otherSize; i++) {
            appendBit(other.get(i));
        }
    }

    public void xor(BitArray other) {
        if (this.bits.length != other.bits.length) {
            throw new IllegalArgumentException("Sizes don't match");
        }
        for (int i = 0; i < this.bits.length; i++) {
            int[] iArr = this.bits;
            iArr[i] = iArr[i] ^ other.bits[i];
        }
    }

    public void toBytes(int bitOffset, byte[] array, int offset, int numBytes) {
        for (int i = 0; i < numBytes; i++) {
            int theByte = 0;
            for (int j = 0; j < 8; j++) {
                if (get(bitOffset)) {
                    theByte |= 1 << (7 - j);
                }
                bitOffset++;
            }
            array[offset + i] = (byte) theByte;
        }
    }

    public int[] getBitArray() {
        return this.bits;
    }

    public void reverse() {
        int[] newBits = new int[this.bits.length];
        int size = this.size;
        for (int i = 0; i < size; i++) {
            if (get((size - i) - 1)) {
                int i2 = i >> 5;
                newBits[i2] = newBits[i2] | (1 << (i & 31));
            }
        }
        this.bits = newBits;
    }

    private static int[] makeArray(int size) {
        return new int[((size + 31) >> 5)];
    }

    public String toString() {
        StringBuilder result = new StringBuilder(this.size);
        for (int i = 0; i < this.size; i++) {
            if ((i & 7) == 0) {
                result.append(TokenParser.SP);
            }
            result.append(get(i) ? 'X' : '.');
        }
        return result.toString();
    }
}
