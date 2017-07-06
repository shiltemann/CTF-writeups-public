package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GeoResultParser extends ResultParser {
    private static final Pattern GEO_URL_PATTERN;

    static {
        GEO_URL_PATTERN = Pattern.compile("geo:([\\-0-9.]+),([\\-0-9.]+)(?:,([\\-0-9.]+))?(?:\\?(.*))?", 2);
    }

    public GeoParsedResult parse(Result result) {
        Matcher matcher = GEO_URL_PATTERN.matcher(ResultParser.getMassagedText(result));
        if (!matcher.matches()) {
            return null;
        }
        String query = matcher.group(4);
        try {
            double latitude = Double.parseDouble(matcher.group(1));
            if (latitude > 90.0d || latitude < -90.0d) {
                return null;
            }
            double longitude = Double.parseDouble(matcher.group(2));
            if (longitude > 180.0d || longitude < -180.0d) {
                return null;
            }
            double altitude;
            if (matcher.group(3) == null) {
                altitude = 0.0d;
            } else {
                altitude = Double.parseDouble(matcher.group(3));
                if (altitude < 0.0d) {
                    return null;
                }
            }
            return new GeoParsedResult(latitude, longitude, altitude, query);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
