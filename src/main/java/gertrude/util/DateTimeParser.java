package gertrude.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeParser {
    // Formatter for file storage
    public static final DateTimeFormatter STORAGE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    // Formatter for display
    public static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy, h:mma");

    // Flexible input patterns
    private static final String[] DATE_TIME_PATTERNS = {
            "d/M/yyyy HHmm",    // Example: 2/12/2019 1800
            "d/M/yyyy HH:mm",   // Example: 2/12/2019 18:00
            "d/M/yyyy h:mma",   // Example: 2/12/2019 6:00am
            "d/M/yyyy ha",      // Example: 2/12/2019 6am
            "d/M/yyyy",         // Example: 2/12/2019
            "yyyy-MM-dd HHmm",  // Example: 2019-12-02 1800
            "yyyy-MM-dd HH:mm", // Example: 2019-12-02 18:00
            "yyyy-MM-dd h:mma", // Example: 2019-12-02 6:00am
            "yyyy-MM-dd ha",    // Example: 2019-12-02 6am
            "yyyy-MM-dd",       // Example: 2019-12-02
            "HHmm",             // Example: 1800
            "HH:mm",            // Example: 18:00
            "h:mma",            // Example: 6:00am
            "ha"                // Example: 6am
    };

    public static LocalDateTime parse(String input) throws IllegalArgumentException {
        try {
            return LocalDateTime.parse(input, STORAGE_FORMAT);
        } catch (DateTimeParseException ignored) {
        } // try with custom patterns
        for (String pattern : DATE_TIME_PATTERNS) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                String lowerPattern = pattern.toLowerCase();
                if (lowerPattern.contains("h")) { // has time component
                    if (!lowerPattern.contains("d")) {
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
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new IllegalArgumentException(
                "Invalid date format. Please use formats like '2/12/2019 1800', '2019-12-02', '6:00am', or 'HH:mm'.");
    }

    public static String[] getAvailableFormats() {
        return DATE_TIME_PATTERNS;
    }
}
