package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeParser {
    // Flexible input patterns
    private static final String[] DATE_TIME_PATTERNS = {
        "d/M/yyyy HHmm", "d/M/yyyy h:mma", "d/M/yyyy HH:mm", "d/M/yyyy",
        "yyyy-MM-dd HHmm", "yyyy-MM-dd HH:mm", "yyyy-MM-dd"
    };

    public static LocalDateTime parse(String input) {
        try {
            return LocalDateTime.parse(input);
        } catch (DateTimeParseException e) {
            // Ignore and try LocalDate
        }
        try {
            return LocalDate.parse(input).atStartOfDay();
        } catch (DateTimeParseException e) {
            // Ignore and try patterns
        }
        for (String pattern : DATE_TIME_PATTERNS) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                if (pattern.toLowerCase().contains("h")) {
                    return LocalDateTime.parse(input, formatter);
                } else {
                    LocalDate date = LocalDate.parse(input, formatter);
                    return date.atStartOfDay();
                }
            } catch (DateTimeParseException ignored) {}
        }
        throw new IllegalArgumentException("Invalid date format. Please use formats like '2/12/2019 1800' or '2019-12-02'.");
    }
}
