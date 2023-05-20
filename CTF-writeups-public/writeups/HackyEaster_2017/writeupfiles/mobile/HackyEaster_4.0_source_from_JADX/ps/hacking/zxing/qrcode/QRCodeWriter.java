package ps.hacking.zxing.qrcode;

import java.util.Map;
import ps.hacking.zxing.BarcodeFormat;
import ps.hacking.zxing.EncodeHintType;
import ps.hacking.zxing.Writer;
import ps.hacking.zxing.WriterException;
import ps.hacking.zxing.common.BitMatrix;
import ps.hacking.zxing.qrcode.decoder.ErrorCorrectionLevel;
import ps.hacking.zxing.qrcode.encoder.ByteMatrix;
import ps.hacking.zxing.qrcode.encoder.Encoder;
import ps.hacking.zxing.qrcode.encoder.QRCode;

public final class QRCodeWriter implements Writer {
    private static final int QUIET_ZONE_SIZE = 4;

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height) throws WriterException {
        return encode(contents, format, width, height, null);
    }

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
        if (contents.length() == 0) {
            throw new IllegalArgumentException("Found empty contents");
        } else if (format != BarcodeFormat.QR_CODE) {
            throw new IllegalArgumentException("Can only encode QR_CODE, but got " + format);
        } else if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Requested dimensions are too small: " + width + 'x' + height);
        } else {
            ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
            int quietZone = QUIET_ZONE_SIZE;
            if (hints != null) {
                ErrorCorrectionLevel requestedECLevel = (ErrorCorrectionLevel) hints.get(EncodeHintType.ERROR_CORRECTION);
                if (requestedECLevel != null) {
                    errorCorrectionLevel = requestedECLevel;
                }
                Integer quietZoneInt = (Integer) hints.get(EncodeHintType.MARGIN);
                if (quietZoneInt != null) {
                    quietZone = quietZoneInt.intValue();
                }
            }
            return renderResult(Encoder.encode(contents, errorCorrectionLevel, hints), width, height, quietZone);
        }
    }

    private static BitMatrix renderResult(QRCode code, int width, int height, int quietZone) {
        ByteMatrix input = code.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        }
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth + (quietZone << 1);
        int qrHeight = inputHeight + (quietZone << 1);
        int outputWidth = Math.max(width, qrWidth);
        int outputHeight = Math.max(height, qrHeight);
        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        int topPadding = (outputHeight - (inputHeight * multiple)) / 2;
        BitMatrix output = new BitMatrix(outputWidth, outputHeight);
        int inputY = 0;
        int outputY = topPadding;
        while (inputY < inputHeight) {
            int inputX = 0;
            int outputX = leftPadding;
            while (inputX < inputWidth) {
                if (input.get(inputX, inputY) == 1) {
                    output.setRegion(outputX, outputY, multiple, multiple);
                }
                inputX++;
                outputX += multiple;
            }
            inputY++;
            outputY += multiple;
        }
        return output;
    }
}
