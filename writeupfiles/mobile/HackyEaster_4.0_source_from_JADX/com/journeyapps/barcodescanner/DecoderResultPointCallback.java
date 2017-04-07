package com.journeyapps.barcodescanner;

import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

public class DecoderResultPointCallback implements ResultPointCallback {
    private Decoder decoder;

    public DecoderResultPointCallback(Decoder decoder) {
        this.decoder = decoder;
    }

    public Decoder getDecoder() {
        return this.decoder;
    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    public void foundPossibleResultPoint(ResultPoint point) {
        if (this.decoder != null) {
            this.decoder.foundPossibleResultPoint(point);
        }
    }
}
