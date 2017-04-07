package com.journeyapps.barcodescanner;

import com.google.zxing.DecodeHintType;
import java.util.Map;

public interface DecoderFactory {
    Decoder createDecoder(Map<DecodeHintType, ?> map);
}
