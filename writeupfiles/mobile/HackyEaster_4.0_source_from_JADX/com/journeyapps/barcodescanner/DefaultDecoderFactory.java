package com.journeyapps.barcodescanner;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

public class DefaultDecoderFactory implements DecoderFactory {
    private String characterSet;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> hints;

    public DefaultDecoderFactory(Collection<BarcodeFormat> decodeFormats, Map<DecodeHintType, ?> hints, String characterSet) {
        this.decodeFormats = decodeFormats;
        this.hints = hints;
        this.characterSet = characterSet;
    }

    public Decoder createDecoder(Map<DecodeHintType, ?> baseHints) {
        Map<DecodeHintType, Object> hints = new EnumMap(DecodeHintType.class);
        hints.putAll(baseHints);
        if (this.hints != null) {
            hints.putAll(this.hints);
        }
        if (this.decodeFormats != null) {
            hints.put(DecodeHintType.POSSIBLE_FORMATS, this.decodeFormats);
        }
        if (this.characterSet != null) {
            hints.put(DecodeHintType.CHARACTER_SET, this.characterSet);
        }
        MultiFormatReader reader = new MultiFormatReader();
        reader.setHints(hints);
        return new Decoder(reader);
    }
}
