package co.parrolabs.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZoneDateTimeUtils {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

    private static ZonedDateTime lastZonedDateTimeGenerated;

    public static String nowUTCAsString() {
        lastZonedDateTimeGenerated = nowUTC();

        return lastZonedDateTimeGenerated.format(formatter);
    }
    public static ZonedDateTime nowUTC() {
        return ZonedDateTime.now(Clock.systemUTC());
    }

    public static String formatDateString(ZonedDateTime zdt) {
        return zdt.format(formatter);
    }

    public static ZonedDateTime stringToZonedDatetime(String str){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
        return LocalDateTime.parse(str, formatter).atZone(ZoneId.of("UTC"));
    }
}
