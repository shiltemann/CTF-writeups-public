package com.google.zxing.client.result;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

public final class CalendarParsedResult extends ParsedResult {
    private static final DateFormat DATE_FORMAT;
    private static final Pattern DATE_TIME;
    private static final DateFormat DATE_TIME_FORMAT;
    private final String[] attendees;
    private final String description;
    private final Date end;
    private final boolean endAllDay;
    private final double latitude;
    private final String location;
    private final double longitude;
    private final String organizer;
    private final Date start;
    private final boolean startAllDay;
    private final String summary;

    static {
        DATE_TIME = Pattern.compile("[0-9]{8}(T[0-9]{6}Z?)?");
        DATE_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
        DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.ENGLISH);
    }

    public CalendarParsedResult(String summary, String startString, String endString, String location, String organizer, String[] attendees, String description, double latitude, double longitude) {
        super(ParsedResultType.CALENDAR);
        this.summary = summary;
        try {
            this.start = parseDate(startString);
            this.end = endString == null ? null : parseDate(endString);
            this.startAllDay = startString.length() == 8;
            boolean z = endString != null && endString.length() == 8;
            this.endAllDay = z;
            this.location = location;
            this.organizer = organizer;
            this.attendees = attendees;
            this.description = description;
            this.latitude = latitude;
            this.longitude = longitude;
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe.toString());
        }
    }

    public String getSummary() {
        return this.summary;
    }

    public Date getStart() {
        return this.start;
    }

    public boolean isStartAllDay() {
        return this.startAllDay;
    }

    public Date getEnd() {
        return this.end;
    }

    public boolean isEndAllDay() {
        return this.endAllDay;
    }

    public String getLocation() {
        return this.location;
    }

    public String getOrganizer() {
        return this.organizer;
    }

    public String[] getAttendees() {
        return this.attendees;
    }

    public String getDescription() {
        return this.description;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getDisplayResult() {
        StringBuilder result = new StringBuilder(100);
        ParsedResult.maybeAppend(this.summary, result);
        ParsedResult.maybeAppend(format(this.startAllDay, this.start), result);
        ParsedResult.maybeAppend(format(this.endAllDay, this.end), result);
        ParsedResult.maybeAppend(this.location, result);
        ParsedResult.maybeAppend(this.organizer, result);
        ParsedResult.maybeAppend(this.attendees, result);
        ParsedResult.maybeAppend(this.description, result);
        return result.toString();
    }

    private static Date parseDate(String when) throws ParseException {
        if (!DATE_TIME.matcher(when).matches()) {
            throw new ParseException(when, 0);
        } else if (when.length() == 8) {
            return DATE_FORMAT.parse(when);
        } else {
            if (when.length() != 16 || when.charAt(15) != 'Z') {
                return DATE_TIME_FORMAT.parse(when);
            }
            Date date = DATE_TIME_FORMAT.parse(when.substring(0, 15));
            Calendar calendar = new GregorianCalendar();
            long milliseconds = date.getTime() + ((long) calendar.get(15));
            calendar.setTime(new Date(milliseconds));
            return new Date(milliseconds + ((long) calendar.get(16)));
        }
    }

    private static String format(boolean allDay, Date date) {
        if (date == null) {
            return null;
        }
        return (allDay ? DateFormat.getDateInstance(2) : DateFormat.getDateTimeInstance(2, 2)).format(date);
    }
}
