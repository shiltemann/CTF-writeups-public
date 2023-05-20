package org.apache.http.client.utils;

import java.lang.ref.SoftReference;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.apache.http.annotation.Immutable;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

@Immutable
public final class DateUtils {
    private static final String[] DEFAULT_PATTERNS;
    private static final Date DEFAULT_TWO_DIGIT_YEAR_START;
    public static final TimeZone GMT;
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
    public static final String PATTERN_RFC1036 = "EEE, dd-MMM-yy HH:mm:ss zzz";
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";

    static final class DateFormatHolder {
        private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> THREADLOCAL_FORMATS;

        /* renamed from: org.apache.http.client.utils.DateUtils.DateFormatHolder.1 */
        static class C00721 extends ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> {
            C00721() {
            }

            protected SoftReference<Map<String, SimpleDateFormat>> initialValue() {
                return new SoftReference(new HashMap());
            }
        }

        DateFormatHolder() {
        }

        static {
            THREADLOCAL_FORMATS = new C00721();
        }

        public static SimpleDateFormat formatFor(String pattern) {
            Map<String, SimpleDateFormat> formats = (Map) ((SoftReference) THREADLOCAL_FORMATS.get()).get();
            if (formats == null) {
                formats = new HashMap();
                THREADLOCAL_FORMATS.set(new SoftReference(formats));
            }
            SimpleDateFormat format = (SimpleDateFormat) formats.get(pattern);
            if (format != null) {
                return format;
            }
            format = new SimpleDateFormat(pattern, Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            formats.put(pattern, format);
            return format;
        }

        public static void clearThreadLocal() {
            THREADLOCAL_FORMATS.remove();
        }
    }

    static {
        DEFAULT_PATTERNS = new String[]{PATTERN_RFC1123, PATTERN_RFC1036, PATTERN_ASCTIME};
        GMT = TimeZone.getTimeZone("GMT");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(GMT);
        calendar.set(2000, 0, 1, 0, 0, 0);
        calendar.set(14, 0);
        DEFAULT_TWO_DIGIT_YEAR_START = calendar.getTime();
    }

    public static Date parseDate(String dateValue) {
        return parseDate(dateValue, null, null);
    }

    public static Date parseDate(String dateValue, String[] dateFormats) {
        return parseDate(dateValue, dateFormats, null);
    }

    public static Date parseDate(String dateValue, String[] dateFormats, Date startDate) {
        Args.notNull(dateValue, "Date value");
        String[] localDateFormats = dateFormats != null ? dateFormats : DEFAULT_PATTERNS;
        Date localStartDate = startDate != null ? startDate : DEFAULT_TWO_DIGIT_YEAR_START;
        String v = dateValue;
        if (v.length() > 1 && v.startsWith("'") && v.endsWith("'")) {
            v = v.substring(1, v.length() - 1);
        }
        for (String dateFormat : localDateFormats) {
            SimpleDateFormat dateParser = DateFormatHolder.formatFor(dateFormat);
            dateParser.set2DigitYearStart(localStartDate);
            ParsePosition pos = new ParsePosition(0);
            Date result = dateParser.parse(v, pos);
            if (pos.getIndex() != 0) {
                return result;
            }
        }
        return null;
    }

    public static String formatDate(Date date) {
        return formatDate(date, PATTERN_RFC1123);
    }

    public static String formatDate(Date date, String pattern) {
        Args.notNull(date, HTTP.DATE_HEADER);
        Args.notNull(pattern, "Pattern");
        return DateFormatHolder.formatFor(pattern).format(date);
    }

    public static void clearThreadLocal() {
        DateFormatHolder.clearThreadLocal();
    }

    private DateUtils() {
    }
}
