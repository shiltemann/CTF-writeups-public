package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import java.util.Map;

public final class ByQuadrantReader implements Reader {
    private final Reader delegate;

    public ByQuadrantReader(Reader delegate) {
        this.delegate = delegate;
    }

    public Result decode(BinaryBitmap image) throws NotFoundException, ChecksumException, FormatException {
        return decode(image, null);
    }

    public Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException, ChecksumException, FormatException {
        int halfWidth = image.getWidth() / 2;
        int halfHeight = image.getHeight() / 2;
        try {
            return this.delegate.decode(image.crop(0, 0, halfWidth, halfHeight), hints);
        } catch (NotFoundException e) {
            try {
                return this.delegate.decode(image.crop(halfWidth, 0, halfWidth, halfHeight), hints);
            } catch (NotFoundException e2) {
                try {
                    return this.delegate.decode(image.crop(0, halfHeight, halfWidth, halfHeight), hints);
                } catch (NotFoundException e3) {
                    try {
                        return this.delegate.decode(image.crop(halfWidth, halfHeight, halfWidth, halfHeight), hints);
                    } catch (NotFoundException e4) {
                        return this.delegate.decode(image.crop(halfWidth / 2, halfHeight / 2, halfWidth, halfHeight), hints);
                    }
                }
            }
        }
    }

    public void reset() {
        this.delegate.reset();
    }
}
