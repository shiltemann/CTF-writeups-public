package ps.hacking.zxing.multi.qrcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ps.hacking.zxing.BarcodeFormat;
import ps.hacking.zxing.BinaryBitmap;
import ps.hacking.zxing.DecodeHintType;
import ps.hacking.zxing.NotFoundException;
import ps.hacking.zxing.ReaderException;
import ps.hacking.zxing.Result;
import ps.hacking.zxing.ResultMetadataType;
import ps.hacking.zxing.common.DecoderResult;
import ps.hacking.zxing.common.DetectorResult;
import ps.hacking.zxing.multi.MultipleBarcodeReader;
import ps.hacking.zxing.multi.qrcode.detector.MultiDetector;
import ps.hacking.zxing.qrcode.QRCodeReader;

public final class QRCodeMultiReader extends QRCodeReader implements MultipleBarcodeReader {
    private static final Result[] EMPTY_RESULT_ARRAY;

    static {
        EMPTY_RESULT_ARRAY = new Result[0];
    }

    public Result[] decodeMultiple(BinaryBitmap image) throws NotFoundException {
        return decodeMultiple(image, null);
    }

    public Result[] decodeMultiple(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException {
        List<Result> results = new ArrayList();
        for (DetectorResult detectorResult : new MultiDetector(image.getBlackMatrix()).detectMulti(hints)) {
            try {
                DecoderResult decoderResult = getDecoder().decode(detectorResult.getBits(), (Map) hints);
                Result result = new Result(decoderResult.getText(), decoderResult.getRawBytes(), detectorResult.getPoints(), BarcodeFormat.QR_CODE);
                List<byte[]> byteSegments = decoderResult.getByteSegments();
                if (byteSegments != null) {
                    result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
                }
                String ecLevel = decoderResult.getECLevel();
                if (ecLevel != null) {
                    result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, ecLevel);
                }
                results.add(result);
            } catch (ReaderException e) {
            }
        }
        if (results.isEmpty()) {
            return EMPTY_RESULT_ARRAY;
        }
        return (Result[]) results.toArray(new Result[results.size()]);
    }
}
