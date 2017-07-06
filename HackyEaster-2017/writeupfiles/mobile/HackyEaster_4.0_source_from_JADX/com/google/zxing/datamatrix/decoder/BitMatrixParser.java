package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;

final class BitMatrixParser {
    private final BitMatrix mappingBitMatrix;
    private final BitMatrix readMappingMatrix;
    private final Version version;

    BitMatrixParser(BitMatrix bitMatrix) throws FormatException {
        int dimension = bitMatrix.getHeight();
        if (dimension < 8 || dimension > 144 || (dimension & 1) != 0) {
            throw FormatException.getFormatInstance();
        }
        this.version = readVersion(bitMatrix);
        this.mappingBitMatrix = extractDataRegion(bitMatrix);
        this.readMappingMatrix = new BitMatrix(this.mappingBitMatrix.getWidth(), this.mappingBitMatrix.getHeight());
    }

    Version getVersion() {
        return this.version;
    }

    private static Version readVersion(BitMatrix bitMatrix) throws FormatException {
        return Version.getVersionForDimensions(bitMatrix.getHeight(), bitMatrix.getWidth());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    byte[] readCodewords() throws com.google.zxing.FormatException {
        /*
        r13 = this;
        r11 = r13.version;
        r11 = r11.getTotalCodewords();
        r7 = new byte[r11];
        r8 = 0;
        r10 = 4;
        r0 = 0;
        r11 = r13.mappingBitMatrix;
        r6 = r11.getHeight();
        r11 = r13.mappingBitMatrix;
        r5 = r11.getWidth();
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r9 = r8;
    L_0x001c:
        if (r10 != r6) goto L_0x0041;
    L_0x001e:
        if (r0 != 0) goto L_0x0041;
    L_0x0020:
        if (r1 != 0) goto L_0x0041;
    L_0x0022:
        r8 = r9 + 1;
        r11 = r13.readCorner1(r6, r5);
        r11 = (byte) r11;
        r7[r9] = r11;
        r10 = r10 + -2;
        r0 = r0 + 2;
        r1 = 1;
    L_0x0030:
        if (r10 < r6) goto L_0x00db;
    L_0x0032:
        if (r0 < r5) goto L_0x00db;
    L_0x0034:
        r11 = r13.version;
        r11 = r11.getTotalCodewords();
        if (r8 == r11) goto L_0x00da;
    L_0x003c:
        r11 = com.google.zxing.FormatException.getFormatInstance();
        throw r11;
    L_0x0041:
        r11 = r6 + -2;
        if (r10 != r11) goto L_0x005c;
    L_0x0045:
        if (r0 != 0) goto L_0x005c;
    L_0x0047:
        r11 = r5 & 3;
        if (r11 == 0) goto L_0x005c;
    L_0x004b:
        if (r2 != 0) goto L_0x005c;
    L_0x004d:
        r8 = r9 + 1;
        r11 = r13.readCorner2(r6, r5);
        r11 = (byte) r11;
        r7[r9] = r11;
        r10 = r10 + -2;
        r0 = r0 + 2;
        r2 = 1;
        goto L_0x0030;
    L_0x005c:
        r11 = r6 + 4;
        if (r10 != r11) goto L_0x0078;
    L_0x0060:
        r11 = 2;
        if (r0 != r11) goto L_0x0078;
    L_0x0063:
        r11 = r5 & 7;
        if (r11 != 0) goto L_0x0078;
    L_0x0067:
        if (r3 != 0) goto L_0x0078;
    L_0x0069:
        r8 = r9 + 1;
        r11 = r13.readCorner3(r6, r5);
        r11 = (byte) r11;
        r7[r9] = r11;
        r10 = r10 + -2;
        r0 = r0 + 2;
        r3 = 1;
        goto L_0x0030;
    L_0x0078:
        r11 = r6 + -2;
        if (r10 != r11) goto L_0x0095;
    L_0x007c:
        if (r0 != 0) goto L_0x0095;
    L_0x007e:
        r11 = r5 & 7;
        r12 = 4;
        if (r11 != r12) goto L_0x0095;
    L_0x0083:
        if (r4 != 0) goto L_0x0095;
    L_0x0085:
        r8 = r9 + 1;
        r11 = r13.readCorner4(r6, r5);
        r11 = (byte) r11;
        r7[r9] = r11;
        r10 = r10 + -2;
        r0 = r0 + 2;
        r4 = 1;
        goto L_0x0030;
    L_0x0094:
        r9 = r8;
    L_0x0095:
        if (r10 >= r6) goto L_0x00e2;
    L_0x0097:
        if (r0 < 0) goto L_0x00e2;
    L_0x0099:
        r11 = r13.readMappingMatrix;
        r11 = r11.get(r0, r10);
        if (r11 != 0) goto L_0x00e2;
    L_0x00a1:
        r8 = r9 + 1;
        r11 = r13.readUtah(r10, r0, r6, r5);
        r11 = (byte) r11;
        r7[r9] = r11;
    L_0x00aa:
        r10 = r10 + -2;
        r0 = r0 + 2;
        if (r10 < 0) goto L_0x00b2;
    L_0x00b0:
        if (r0 < r5) goto L_0x0094;
    L_0x00b2:
        r10 = r10 + 1;
        r0 = r0 + 3;
        r9 = r8;
    L_0x00b7:
        if (r10 < 0) goto L_0x00e0;
    L_0x00b9:
        if (r0 >= r5) goto L_0x00e0;
    L_0x00bb:
        r11 = r13.readMappingMatrix;
        r11 = r11.get(r0, r10);
        if (r11 != 0) goto L_0x00e0;
    L_0x00c3:
        r8 = r9 + 1;
        r11 = r13.readUtah(r10, r0, r6, r5);
        r11 = (byte) r11;
        r7[r9] = r11;
    L_0x00cc:
        r10 = r10 + 2;
        r0 = r0 + -2;
        if (r10 >= r6) goto L_0x00d4;
    L_0x00d2:
        if (r0 >= 0) goto L_0x00de;
    L_0x00d4:
        r10 = r10 + 3;
        r0 = r0 + 1;
        goto L_0x0030;
    L_0x00da:
        return r7;
    L_0x00db:
        r9 = r8;
        goto L_0x001c;
    L_0x00de:
        r9 = r8;
        goto L_0x00b7;
    L_0x00e0:
        r8 = r9;
        goto L_0x00cc;
    L_0x00e2:
        r8 = r9;
        goto L_0x00aa;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.decoder.BitMatrixParser.readCodewords():byte[]");
    }

    boolean readModule(int row, int column, int numRows, int numColumns) {
        if (row < 0) {
            row += numRows;
            column += 4 - ((numRows + 4) & 7);
        }
        if (column < 0) {
            column += numColumns;
            row += 4 - ((numColumns + 4) & 7);
        }
        this.readMappingMatrix.set(column, row);
        return this.mappingBitMatrix.get(column, row);
    }

    int readUtah(int row, int column, int numRows, int numColumns) {
        int currentByte = 0;
        if (readModule(row - 2, column - 2, numRows, numColumns)) {
            currentByte = 0 | 1;
        }
        currentByte <<= 1;
        if (readModule(row - 2, column - 1, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(row - 1, column - 2, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(row - 1, column - 1, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(row - 1, column, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(row, column - 2, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(row, column - 1, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(row, column, numRows, numColumns)) {
            return currentByte | 1;
        }
        return currentByte;
    }

    int readCorner1(int numRows, int numColumns) {
        int currentByte = 0;
        if (readModule(numRows - 1, 0, numRows, numColumns)) {
            currentByte = 0 | 1;
        }
        currentByte <<= 1;
        if (readModule(numRows - 1, 1, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(numRows - 1, 2, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(0, numColumns - 2, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(0, numColumns - 1, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(1, numColumns - 1, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(2, numColumns - 1, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(3, numColumns - 1, numRows, numColumns)) {
            return currentByte | 1;
        }
        return currentByte;
    }

    int readCorner2(int numRows, int numColumns) {
        int currentByte = 0;
        if (readModule(numRows - 3, 0, numRows, numColumns)) {
            currentByte = 0 | 1;
        }
        currentByte <<= 1;
        if (readModule(numRows - 2, 0, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(numRows - 1, 0, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(0, numColumns - 4, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(0, numColumns - 3, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(0, numColumns - 2, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(0, numColumns - 1, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(1, numColumns - 1, numRows, numColumns)) {
            return currentByte | 1;
        }
        return currentByte;
    }

    int readCorner3(int numRows, int numColumns) {
        int currentByte = 0;
        if (readModule(numRows - 1, 0, numRows, numColumns)) {
            currentByte = 0 | 1;
        }
        currentByte <<= 1;
        if (readModule(numRows - 1, numColumns - 1, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(0, numColumns - 3, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(0, numColumns - 2, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(0, numColumns - 1, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(1, numColumns - 3, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(1, numColumns - 2, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(1, numColumns - 1, numRows, numColumns)) {
            return currentByte | 1;
        }
        return currentByte;
    }

    int readCorner4(int numRows, int numColumns) {
        int currentByte = 0;
        if (readModule(numRows - 3, 0, numRows, numColumns)) {
            currentByte = 0 | 1;
        }
        currentByte <<= 1;
        if (readModule(numRows - 2, 0, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(numRows - 1, 0, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(0, numColumns - 2, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(0, numColumns - 1, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(1, numColumns - 1, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(2, numColumns - 1, numRows, numColumns)) {
            currentByte |= 1;
        }
        currentByte <<= 1;
        if (readModule(3, numColumns - 1, numRows, numColumns)) {
            return currentByte | 1;
        }
        return currentByte;
    }

    BitMatrix extractDataRegion(BitMatrix bitMatrix) {
        int symbolSizeRows = this.version.getSymbolSizeRows();
        int symbolSizeColumns = this.version.getSymbolSizeColumns();
        if (bitMatrix.getHeight() != symbolSizeRows) {
            throw new IllegalArgumentException("Dimension of bitMarix must match the version size");
        }
        int dataRegionSizeRows = this.version.getDataRegionSizeRows();
        int dataRegionSizeColumns = this.version.getDataRegionSizeColumns();
        int numDataRegionsRow = symbolSizeRows / dataRegionSizeRows;
        int numDataRegionsColumn = symbolSizeColumns / dataRegionSizeColumns;
        BitMatrix bitMatrixWithoutAlignment = new BitMatrix(numDataRegionsColumn * dataRegionSizeColumns, numDataRegionsRow * dataRegionSizeRows);
        for (int dataRegionRow = 0; dataRegionRow < numDataRegionsRow; dataRegionRow++) {
            int dataRegionRowOffset = dataRegionRow * dataRegionSizeRows;
            for (int dataRegionColumn = 0; dataRegionColumn < numDataRegionsColumn; dataRegionColumn++) {
                int dataRegionColumnOffset = dataRegionColumn * dataRegionSizeColumns;
                for (int i = 0; i < dataRegionSizeRows; i++) {
                    int readRowOffset = (((dataRegionSizeRows + 2) * dataRegionRow) + 1) + i;
                    int writeRowOffset = dataRegionRowOffset + i;
                    for (int j = 0; j < dataRegionSizeColumns; j++) {
                        if (bitMatrix.get((((dataRegionSizeColumns + 2) * dataRegionColumn) + 1) + j, readRowOffset)) {
                            bitMatrixWithoutAlignment.set(dataRegionColumnOffset + j, writeRowOffset);
                        }
                    }
                }
            }
        }
        return bitMatrixWithoutAlignment;
    }
}
