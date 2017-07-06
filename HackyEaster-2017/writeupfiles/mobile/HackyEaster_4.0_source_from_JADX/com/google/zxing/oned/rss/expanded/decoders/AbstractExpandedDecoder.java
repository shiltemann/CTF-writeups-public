package com.google.zxing.oned.rss.expanded.decoders;

import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.view.MotionEventCompat;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import org.apache.http.protocol.HTTP;

public abstract class AbstractExpandedDecoder {
    private final GeneralAppIdDecoder generalDecoder;
    private final BitArray information;

    public abstract String parseInformation() throws NotFoundException;

    AbstractExpandedDecoder(BitArray information) {
        this.information = information;
        this.generalDecoder = new GeneralAppIdDecoder(information);
    }

    protected final BitArray getInformation() {
        return this.information;
    }

    protected final GeneralAppIdDecoder getGeneralDecoder() {
        return this.generalDecoder;
    }

    public static AbstractExpandedDecoder createDecoder(BitArray information) {
        if (information.get(1)) {
            return new AI01AndOtherAIs(information);
        }
        if (!information.get(2)) {
            return new AnyAIDecoder(information);
        }
        switch (GeneralAppIdDecoder.extractNumericValueFromBitArray(information, 1, 4)) {
            case WearableExtender.SIZE_LARGE /*4*/:
                return new AI013103decoder(information);
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                return new AI01320xDecoder(information);
            default:
                switch (GeneralAppIdDecoder.extractNumericValueFromBitArray(information, 1, 5)) {
                    case MotionEventCompat.AXIS_RX /*12*/:
                        return new AI01392xDecoder(information);
                    case HTTP.CR /*13*/:
                        return new AI01393xDecoder(information);
                    default:
                        switch (GeneralAppIdDecoder.extractNumericValueFromBitArray(information, 1, 7)) {
                            case 56:
                                return new AI013x0x1xDecoder(information, "310", "11");
                            case 57:
                                return new AI013x0x1xDecoder(information, "320", "11");
                            case 58:
                                return new AI013x0x1xDecoder(information, "310", "13");
                            case 59:
                                return new AI013x0x1xDecoder(information, "320", "13");
                            case 60:
                                return new AI013x0x1xDecoder(information, "310", "15");
                            case 61:
                                return new AI013x0x1xDecoder(information, "320", "15");
                            case 62:
                                return new AI013x0x1xDecoder(information, "310", "17");
                            case 63:
                                return new AI013x0x1xDecoder(information, "320", "17");
                            default:
                                throw new IllegalStateException("unknown decoder: " + information);
                        }
                }
        }
    }
}
