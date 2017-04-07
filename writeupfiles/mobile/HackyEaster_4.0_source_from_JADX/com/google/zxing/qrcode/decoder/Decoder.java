package com.google.zxing.qrcode.decoder;

import android.support.v4.view.MotionEventCompat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import java.util.Map;

public final class Decoder {
    private final ReedSolomonDecoder rsDecoder;

    public Decoder() {
        this.rsDecoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);
    }

    public DecoderResult decode(boolean[][] image) throws ChecksumException, FormatException {
        return decode(image, null);
    }

    public DecoderResult decode(boolean[][] image, Map<DecodeHintType, ?> hints) throws ChecksumException, FormatException {
        int dimension = image.length;
        BitMatrix bits = new BitMatrix(dimension);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (image[i][j]) {
                    bits.set(j, i);
                }
            }
        }
        return decode(bits, (Map) hints);
    }

    public DecoderResult decode(BitMatrix bits) throws ChecksumException, FormatException {
        return decode(bits, null);
    }

    public DecoderResult decode(BitMatrix bits, Map<DecodeHintType, ?> hints) throws FormatException, ChecksumException {
        DataBlock dataBlock;
        BitMatrixParser parser = new BitMatrixParser(bits);
        Version version = parser.readVersion();
        ErrorCorrectionLevel ecLevel = parser.readFormatInformation().getErrorCorrectionLevel();
        DataBlock[] dataBlocks = DataBlock.getDataBlocks(parser.readCodewords(), version, ecLevel);
        int totalBytes = 0;
        for (DataBlock dataBlock2 : dataBlocks) {
            totalBytes += dataBlock2.getNumDataCodewords();
        }
        byte[] resultBytes = new byte[totalBytes];
        int resultOffset = 0;
        DataBlock[] arr$ = dataBlocks;
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            dataBlock2 = arr$[i$];
            byte[] codewordBytes = dataBlock2.getCodewords();
            int numDataCodewords = dataBlock2.getNumDataCodewords();
            correctErrors(codewordBytes, numDataCodewords);
            int i = 0;
            int resultOffset2 = resultOffset;
            while (i < numDataCodewords) {
                resultOffset = resultOffset2 + 1;
                resultBytes[resultOffset2] = codewordBytes[i];
                i++;
                resultOffset2 = resultOffset;
            }
            i$++;
            resultOffset = resultOffset2;
        }
        return DecodedBitStreamParser.decode(resultBytes, version, ecLevel, hints);
    }

    private void correctErrors(byte[] codewordBytes, int numDataCodewords) throws ChecksumException {
        int i;
        int numCodewords = codewordBytes.length;
        int[] codewordsInts = new int[numCodewords];
        for (i = 0; i < numCodewords; i++) {
            codewordsInts[i] = codewordBytes[i] & MotionEventCompat.ACTION_MASK;
        }
        try {
            this.rsDecoder.decode(codewordsInts, codewordBytes.length - numDataCodewords);
            for (i = 0; i < numDataCodewords; i++) {
                codewordBytes[i] = (byte) codewordsInts[i];
            }
        } catch (ReedSolomonException e) {
            throw ChecksumException.getChecksumInstance();
        }
    }
}
