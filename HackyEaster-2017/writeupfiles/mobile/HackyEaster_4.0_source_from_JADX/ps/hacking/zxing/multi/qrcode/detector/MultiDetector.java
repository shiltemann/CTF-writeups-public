package ps.hacking.zxing.multi.qrcode.detector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ps.hacking.zxing.DecodeHintType;
import ps.hacking.zxing.NotFoundException;
import ps.hacking.zxing.ReaderException;
import ps.hacking.zxing.ResultPointCallback;
import ps.hacking.zxing.common.BitMatrix;
import ps.hacking.zxing.common.DetectorResult;
import ps.hacking.zxing.qrcode.detector.Detector;
import ps.hacking.zxing.qrcode.detector.FinderPatternInfo;

public final class MultiDetector extends Detector {
    private static final DetectorResult[] EMPTY_DETECTOR_RESULTS;

    static {
        EMPTY_DETECTOR_RESULTS = new DetectorResult[0];
    }

    public MultiDetector(BitMatrix image) {
        super(image);
    }

    public DetectorResult[] detectMulti(Map<DecodeHintType, ?> hints) throws NotFoundException {
        FinderPatternInfo[] infos = new MultiFinderPatternFinder(getImage(), hints == null ? null : (ResultPointCallback) hints.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK)).findMulti(hints);
        if (infos.length == 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        List<DetectorResult> result = new ArrayList();
        for (FinderPatternInfo info : infos) {
            try {
                result.add(processFinderPatternInfo(info));
            } catch (ReaderException e) {
            }
        }
        if (result.isEmpty()) {
            return EMPTY_DETECTOR_RESULTS;
        }
        return (DetectorResult[]) result.toArray(new DetectorResult[result.size()]);
    }
}
