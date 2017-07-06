package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.qrcode.detector.Detector;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
