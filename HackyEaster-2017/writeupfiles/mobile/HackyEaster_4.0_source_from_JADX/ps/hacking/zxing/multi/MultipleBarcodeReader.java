package ps.hacking.zxing.multi;

import java.util.Map;
import ps.hacking.zxing.BinaryBitmap;
import ps.hacking.zxing.DecodeHintType;
import ps.hacking.zxing.NotFoundException;
import ps.hacking.zxing.Result;

public interface MultipleBarcodeReader {
    Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException;

    Result[] decodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException;
}
