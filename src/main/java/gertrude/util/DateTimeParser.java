package gertrude.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.stream.Stream;

/**
 * Utility class for parsing and formatting date and time strings.
 */
public class DateTimeParser {
    // Formatter for file storage
    public static final DateTimeFormatter STORAGE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    // Formatter for display
    public static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy, h:mma");
    private static final Logger logger = Logger.getLogger("DateTimeParserLogger");

    // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
    // Flexible input patterns
    // Date-only patterns
    private static final String[] DATE_PATTERNS = {
            "d/M/yyyy", // Example: 2/12/2019
            "yyyy-MM-dd", // Example: 2019-12-02
            "E", // Example: Tuesday
            "e" // Example: Tue
    };

    // Time-only patterns
    private static final String[] TIME_PATTERNS = {
            "HHmm", // Example: 1800
            "HH:mm", // Example: 18:00
            "h:mma", // Example: 6:00am
            "ha" // Example: 6am
    };

    /**
     * Parses a date-time string into a LocalDateTime object.
     *
     * @param input The date-time string to parse.
     * @return The parsed LocalDateTime object.
     * @throws IllegalArgumentException If the input cannot be parsed.
     */
    public static LocalDateTime parse(String input) throws IllegalArgumentException {
        assert STORAGE_FORMAT != null : "missing STORAGE_FORMAT";
        assert DATE_PATTERNS != null && DATE_PATTERNS.length > 0 : "missing DATE_PATTERNS";
        assert TIME_PATTERNS != null && TIME_PATTERNS.length > 0 : "missing TIME_PATTERNS";

        try {
            return LocalDateTime.parse(input, STORAGE_FORMAT);
        } catch (DateTimeParseException ignored) {
            // try with custom patterns if default parsing fails
        }

        String[] parts = input.split("\\s+");
        LocalDate date = null;
        LocalTime time = null;

        for (String part : parts) {
            try {
                // Check if the part is a day of the week
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("E", Locale.ENGLISH);
                DayOfWeek dayOfWeek = DayOfWeek.from(fmt.parse(part));
                LocalDate today = LocalDate.now();
                date = today.with(TemporalAdjusters.nextOrSame(dayOfWeek));
            } catch (DateTimeParseException ignored) {
                // Not a day of the week, continue
            }

            if (date == null) {
                // Try parsing as a date
                for (String pattern : DATE_PATTERNS) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                        date = LocalDate.parse(part, formatter);
                        break;
                    } catch (DateTimeParseException ignored) {
                        // Try the next pattern
                    }
                }
            }

            if (time == null) {
                // Try parsing as a time
                for (String pattern : TIME_PATTERNS) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                        time = LocalTime.parse(part, formatter);
                        break;
                    } catch (DateTimeParseException ignored) {
                        // Try the next pattern
                    }
                }
            }
        }

        if (date == null && time == null) {
            throw new IllegalArgumentException("Unable to parse date/time from input: " + input);
        }

        // Default to current date if no date is provided
        if (date == null) {
            logger.log(Level.INFO, "No date provided, defaulting to today.");
            date = LocalDate.now();

            // Default to current time if no time is provided
            if (time == null) {
                time = LocalTime.now();
            }

            LocalDateTime dateTime = date.atTime(time);

            // If the time has already passed today, use the next day
            if (dateTime.isBefore(LocalDateTime.now())) {
                dateTime = dateTime.plusDays(1);
            }
        }

        // Default to 8 AM if no time is provided
        if (time == null) {
            time = LocalTime.of(8, 0);
        }

        LocalDateTime dateTime = date.atTime(time);

        return dateTime;
    }

    /**
     * Returns the available date-time formats supported by the parser.
     *
     * @return An array of supported date-time formats.
     */
    public static String[] getAvailableFormats() {
        Stream<String> datePatternsStream = Stream.of(DATE_PATTERNS);
        Stream<String> timePatternsStream = Stream.of(TIME_PATTERNS);
        return Stream.concat(datePatternsStream, timePatternsStream).toArray(String[]::new);
    }
}
