package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeParser {
    // Flexible input patterns
    private static final String[] DATE_TIME_PATTERNS = {
        "d/M/yyyy HHmm",    // Example: 2/12/2019 1800
        "d/M/yyyy H:mma",   // Example: 2/12/2019 6:00am
        "d/M/yyyy HH:mm",   // Example: 2/12/2019 18:00
        "d/M/yyyy",         // Example: 2/12/2019
        "yyyy-MM-dd HHmm",  // Example: 2019-12-02 1800
        "yyyy-MM-dd H:mma", // Example: 2019-12-02 6:00am
        "yyyy-MM-dd HH:mm", // Example: 2019-12-02 18:00
        "yyyy-MM-dd",       // Example: 2019-12-02
        "HHmm",             // Example: 1800
        "H:mma",            // Example: 6:00am
        "HH:mm"             // Example: 18:00
    };

    public static LocalDateTime parse(String input) throws IllegalArgumentException {
        for (String pattern : DATE_TIME_PATTERNS) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                if (pattern.contains("H")) { // has time component
                    if (pattern.equals("HHmm") || pattern.equals("H:mma") || pattern.equals("HH:mm")) {
                        // Handle time-only formats
                        LocalTime time = LocalTime.parse(input, formatter);
                        LocalDate today = LocalDate.now();
                        LocalDateTime dateTime = today.atTime(time);
                        // If the time has already passed today, use the next day
                        if (dateTime.isBefore(LocalDateTime.now())) {
                            dateTime = dateTime.plusDays(1);
                        }
                        return dateTime;
                    } else {
                        return LocalDateTime.parse(input, formatter);
                    }
                } else {
                    LocalDate date = LocalDate.parse(input, formatter);
                    return date.atStartOfDay();
                }
            } catch (DateTimeParseException ignored) {}
        }
        throw new IllegalArgumentException("Invalid date format. Please use formats like '2/12/2019 1800', '2019-12-02', '6:00am', or 'HH:mm'.");
    }

    public static String[] getAvailableFormats() {
        return DATE_TIME_PATTERNS;
    }
}
