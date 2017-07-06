package com.google.zxing.qrcode.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;

final class BitMatrixParser {
    private final BitMatrix bitMatrix;
    private FormatInformation parsedFormatInfo;
    private Version parsedVersion;

    BitMatrixParser(BitMatrix bitMatrix) throws FormatException {
        int dimension = bitMatrix.getHeight();
        if (dimension < 21 || (dimension & 3) != 1) {
            throw FormatException.getFormatInstance();
        }
        this.bitMatrix = bitMatrix;
    }

    FormatInformation readFormatInformation() throws FormatException {
        if (this.parsedFormatInfo != null) {
            return this.parsedFormatInfo;
        }
        int i;
        int j;
        int formatInfoBits1 = 0;
        for (i = 0; i < 6; i++) {
            formatInfoBits1 = copyBit(i, 8, formatInfoBits1);
        }
        formatInfoBits1 = copyBit(8, 7, copyBit(8, 8, copyBit(7, 8, formatInfoBits1)));
        for (j = 5; j >= 0; j--) {
            formatInfoBits1 = copyBit(8, j, formatInfoBits1);
        }
        int dimension = this.bitMatrix.getHeight();
        int formatInfoBits2 = 0;
        int jMin = dimension - 7;
        for (j = dimension - 1; j >= jMin; j--) {
            formatInfoBits2 = copyBit(8, j, formatInfoBits2);
        }
        for (i = dimension - 8; i < dimension; i++) {
            formatInfoBits2 = copyBit(i, 8, formatInfoBits2);
        }
        this.parsedFormatInfo = FormatInformation.decodeFormatInformation(formatInfoBits1, formatInfoBits2);
        if (this.parsedFormatInfo != null) {
            return this.parsedFormatInfo;
        }
        throw FormatException.getFormatInstance();
    }

    Version readVersion() throws FormatException {
        if (this.parsedVersion != null) {
            return this.parsedVersion;
        }
        int dimension = this.bitMatrix.getHeight();
        int provisionalVersion = (dimension - 17) >> 2;
        if (provisionalVersion <= 6) {
            return Version.getVersionForNumber(provisionalVersion);
        }
        int j;
        int i;
        int versionBits = 0;
        int ijMin = dimension - 11;
        for (j = 5; j >= 0; j--) {
            for (i = dimension - 9; i >= ijMin; i--) {
                versionBits = copyBit(i, j, versionBits);
            }
        }
        Version theParsedVersion = Version.decodeVersionInformation(versionBits);
        if (theParsedVersion == null || theParsedVersion.getDimensionForVersion() != dimension) {
            versionBits = 0;
            for (i = 5; i >= 0; i--) {
                for (j = dimension - 9; j >= ijMin; j--) {
                    versionBits = copyBit(i, j, versionBits);
                }
            }
            theParsedVersion = Version.decodeVersionInformation(versionBits);
            if (theParsedVersion == null || theParsedVersion.getDimensionForVersion() != dimension) {
                throw FormatException.getFormatInstance();
            }
            this.parsedVersion = theParsedVersion;
            return theParsedVersion;
        }
        this.parsedVersion = theParsedVersion;
        return theParsedVersion;
    }

    private int copyBit(int i, int j, int versionBits) {
        return this.bitMatrix.get(i, j) ? (versionBits << 1) | 1 : versionBits << 1;
    }

    byte[] readCodewords() throws FormatException {
        FormatInformation formatInfo = readFormatInformation();
        Version version = readVersion();
        DataMask dataMask = DataMask.forReference(formatInfo.getDataMask());
        int dimension = this.bitMatrix.getHeight();
        dataMask.unmaskBitMatrix(this.bitMatrix, dimension);
        BitMatrix functionPattern = version.buildFunctionPattern();
        boolean readingUp = true;
        byte[] result = new byte[version.getTotalCodewords()];
        int resultOffset = 0;
        int currentByte = 0;
        int bitsRead = 0;
        int j = dimension - 1;
        while (j > 0) {
            if (j == 6) {
                j--;
            }
            int count = 0;
            while (count < dimension) {
                int i;
                if (readingUp) {
                    i = (dimension - 1) - count;
                } else {
                    i = count;
                }
                int col = 0;
                int resultOffset2 = resultOffset;
                while (col < 2) {
                    if (!functionPattern.get(j - col, i)) {
                        bitsRead++;
                        currentByte <<= 1;
                        if (this.bitMatrix.get(j - col, i)) {
                            currentByte |= 1;
                        }
                        if (bitsRead == 8) {
                            resultOffset = resultOffset2 + 1;
                            result[resultOffset2] = (byte) currentByte;
                            bitsRead = 0;
                            currentByte = 0;
                            col++;
                            resultOffset2 = resultOffset;
                        }
                    }
                    resultOffset = resultOffset2;
                    col++;
                    resultOffset2 = resultOffset;
                }
                count++;
                resultOffset = resultOffset2;
            }
            readingUp ^= 1;
            j -= 2;
        }
        if (resultOffset == version.getTotalCodewords()) {
            return result;
        }
        throw FormatException.getFormatInstance();
    }
}
