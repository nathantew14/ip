package gertrude.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeParserTest {

    @Test
    void parse_validDateTimeFormats_shouldReturnCorrectLocalDateTime() {
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0),
                DateTimeParser.parse("2/12/2019 1800"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0),
                DateTimeParser.parse("2/12/2019 18:00"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 6, 0),
                DateTimeParser.parse("2/12/2019 6:00am"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 6, 0),
                DateTimeParser.parse("2/12/2019 6am"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 0, 0),
                DateTimeParser.parse("2/12/2019"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0),
                DateTimeParser.parse("2019-12-02 1800"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0),
                DateTimeParser.parse("2019-12-02 18:00"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 6, 0),
                DateTimeParser.parse("2019-12-02 6:00am"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 6, 0),
                DateTimeParser.parse("2019-12-02 6am"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 0, 0),
                DateTimeParser.parse("2019-12-02"));
    }

    @Test
    void parse_timeOnlyFormats_shouldReturnCorrectLocalDateTime() {
        // i know this is not proper unit testing because it's using LocalDate.now() but
        // i
        // really don't have time to investigate how to do this properly right now so
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        String input = "1800";
        LocalDateTime parsedTime = DateTimeParser.parse(input);
        LocalTime actualTime = LocalTime.parse(input, DateTimeFormatter.ofPattern("HHmm"));
        LocalDateTime dateTime = today.atTime(actualTime);
        if (dateTime.isBefore(now)) {
            dateTime = dateTime.plusDays(1);
        }
        assertEquals(dateTime, parsedTime);

        input = "6:00am";
        parsedTime = DateTimeParser.parse(input);
        actualTime = LocalTime.parse(input, DateTimeFormatter.ofPattern("h:mma"));
        dateTime = today.atTime(actualTime);
        if (dateTime.isBefore(now)) {
            dateTime = dateTime.plusDays(1);
        }
        assertEquals(dateTime, parsedTime);
    }

    @Test
    void parse_invalidFormats_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> DateTimeParser.parse("invalid-date"));
        assertThrows(IllegalArgumentException.class, () -> DateTimeParser.parse("32/12/2019"));
        assertThrows(IllegalArgumentException.class, () -> DateTimeParser.parse("2019-13-02"));
        assertThrows(IllegalArgumentException.class, () -> DateTimeParser.parse("25:00"));
    }
}
