package com.journeyapps.barcodescanner;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.HybridBinarizer;
import java.util.ArrayList;
import java.util.List;

public class Decoder implements ResultPointCallback {
    private List<ResultPoint> possibleResultPoints;
    private Reader reader;

    public Decoder(Reader reader) {
        this.possibleResultPoints = new ArrayList();
        this.reader = reader;
    }

    protected Reader getReader() {
        return this.reader;
    }

    public Result decode(LuminanceSource source) {
        return decode(toBitmap(source));
    }

    protected BinaryBitmap toBitmap(LuminanceSource source) {
        return new BinaryBitmap(new HybridBinarizer(source));
    }

    protected Result decode(BinaryBitmap bitmap) {
        this.possibleResultPoints.clear();
        Result decodeWithState;
        try {
            if (this.reader instanceof MultiFormatReader) {
                decodeWithState = ((MultiFormatReader) this.reader).decodeWithState(bitmap);
                return decodeWithState;
            }
            decodeWithState = this.reader.decode(bitmap);
            this.reader.reset();
            return decodeWithState;
        } catch (Exception e) {
            decodeWithState = null;
        } finally {
            this.reader.reset();
        }
    }

    public List<ResultPoint> getPossibleResultPoints() {
        return new ArrayList(this.possibleResultPoints);
    }

    public void foundPossibleResultPoint(ResultPoint point) {
        this.possibleResultPoints.add(point);
    }
}
