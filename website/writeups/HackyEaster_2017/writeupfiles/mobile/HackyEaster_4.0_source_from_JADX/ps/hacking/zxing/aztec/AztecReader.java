package ps.hacking.zxing.aztec;

import java.util.List;
import java.util.Map;
import ps.hacking.zxing.BarcodeFormat;
import ps.hacking.zxing.BinaryBitmap;
import ps.hacking.zxing.DecodeHintType;
import ps.hacking.zxing.FormatException;
import ps.hacking.zxing.NotFoundException;
import ps.hacking.zxing.Reader;
import ps.hacking.zxing.Result;
import ps.hacking.zxing.ResultMetadataType;
import ps.hacking.zxing.ResultPoint;
import ps.hacking.zxing.ResultPointCallback;
import ps.hacking.zxing.aztec.decoder.Decoder;
import ps.hacking.zxing.aztec.detector.Detector;
import ps.hacking.zxing.common.DecoderResult;

public final class AztecReader implements Reader {
    public Result decode(BinaryBitmap image) throws NotFoundException, FormatException {
        return decode(image, null);
    }

    public Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException, FormatException {
        AztecDetectorResult detectorResult = new Detector(image.getBlackMatrix()).detect();
        ResultPoint[] points = detectorResult.getPoints();
        if (hints != null) {
            ResultPointCallback rpcb = (ResultPointCallback) hints.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
            if (rpcb != null) {
                for (ResultPoint point : points) {
                    rpcb.foundPossibleResultPoint(point);
                }
            }
        }
        DecoderResult decoderResult = new Decoder().decode(detectorResult);
        Result result = new Result(decoderResult.getText(), decoderResult.getRawBytes(), points, BarcodeFormat.AZTEC);
        List<byte[]> byteSegments = decoderResult.getByteSegments();
        if (byteSegments != null) {
            result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
        }
        String ecLevel = decoderResult.getECLevel();
        if (ecLevel != null) {
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, ecLevel);
        }
        return result;
    }

    public void reset() {
    }
}
