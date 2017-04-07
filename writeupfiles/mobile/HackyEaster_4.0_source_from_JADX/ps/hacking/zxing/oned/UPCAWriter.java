package ps.hacking.zxing.oned;

import java.util.Map;
import ps.hacking.zxing.BarcodeFormat;
import ps.hacking.zxing.EncodeHintType;
import ps.hacking.zxing.Writer;
import ps.hacking.zxing.WriterException;
import ps.hacking.zxing.common.BitMatrix;

public final class UPCAWriter implements Writer {
    private final EAN13Writer subWriter;

    public UPCAWriter() {
        this.subWriter = new EAN13Writer();
    }

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height) throws WriterException {
        return encode(contents, format, width, height, null);
    }

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
        if (format == BarcodeFormat.UPC_A) {
            return this.subWriter.encode(preencode(contents), BarcodeFormat.EAN_13, width, height, hints);
        }
        throw new IllegalArgumentException("Can only encode UPC-A, but got " + format);
    }

    private static String preencode(String contents) {
        int length = contents.length();
        if (length == 11) {
            int sum = 0;
            for (int i = 0; i < 11; i++) {
                sum += (i % 2 == 0 ? 3 : 1) * (contents.charAt(i) - 48);
            }
            contents = contents + ((1000 - sum) % 10);
        } else if (length != 12) {
            throw new IllegalArgumentException("Requested contents should be 11 or 12 digits long, but got " + contents.length());
        }
        return '0' + contents;
    }
}
