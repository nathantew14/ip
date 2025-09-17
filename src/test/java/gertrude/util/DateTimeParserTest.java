package gertrude.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeParserTest {

    @Test
    void parse_validDateTimeFormats_shouldReturnCorrectLocalDateTime() {
        assertEquals(LocalDateTime.of(2025, 12, 2, 18, 0),
                DateTimeParser.parse("2/12/2025 1800"));
        assertEquals(LocalDateTime.of(2025, 12, 2, 18, 0),
                DateTimeParser.parse("2/12/2025 18:00"));
        assertEquals(LocalDateTime.of(2025, 12, 2, 6, 0),
                DateTimeParser.parse("2/12/2025 6:00am"));
        assertEquals(LocalDateTime.of(2025, 12, 2, 6, 0),
                DateTimeParser.parse("2/12/2025 6am"));
        assertEquals(LocalDateTime.of(2025, 12, 2, 8, 0),
                DateTimeParser.parse("2/12/2025"));
        assertEquals(LocalDateTime.of(2025, 12, 2, 18, 0),
                DateTimeParser.parse("2025-12-02 1800"));
        assertEquals(LocalDateTime.of(2025, 12, 2, 18, 0),
                DateTimeParser.parse("2025-12-02 18:00"));
        assertEquals(LocalDateTime.of(2025, 12, 2, 6, 0),
                DateTimeParser.parse("2025-12-02 6:00am"));
        assertEquals(LocalDateTime.of(2025, 12, 2, 6, 0),
                DateTimeParser.parse("2025-12-02 6am"));
        assertEquals(LocalDateTime.of(2025, 12, 2, 8, 0),
                DateTimeParser.parse("2025-12-02"));
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
        assertThrows(IllegalArgumentException.class, () -> DateTimeParser.parse("32/12/2025"));
        assertThrows(IllegalArgumentException.class, () -> DateTimeParser.parse("2025-13-02"));
        assertThrows(IllegalArgumentException.class, () -> DateTimeParser.parse("25:00"));
    }

    @Test
    void parseSpecialWords_shouldReturnCorrectDate() {
        assertEquals(LocalDate.now(), DateTimeParser.parseSpecialWords("today"));
        assertEquals(LocalDate.now().plusDays(1), DateTimeParser.parseSpecialWords("tomorrow"));
        assertNull(DateTimeParser.parseSpecialWords("random"));
    }

    @Test
    void parseDayOfWeek_shouldReturnCorrectDate() {
        LocalDate today = LocalDate.now();
        assertEquals(today.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)),
                DateTimeParser.parseDayOfWeek("Monday"));
        assertEquals(today.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)),
                DateTimeParser.parseDayOfWeek("Friday"));
        assertNull(DateTimeParser.parseDayOfWeek("NotADay"));
    }

    @Test
    void parseDate_shouldReturnCorrectDate() {
        assertEquals(LocalDate.of(2025, 12, 2), DateTimeParser.parseDate("2/12/2025"));
        assertEquals(LocalDate.of(2025, 12, 2), DateTimeParser.parseDate("2025-12-02"));
        assertNull(DateTimeParser.parseDate("invalid-date"));
    }

    @Test
    void parseTime_shouldReturnCorrectTime() {
        assertEquals(LocalTime.of(18, 0), DateTimeParser.parseTime("1800"));
        assertEquals(LocalTime.of(6, 0), DateTimeParser.parseTime("6:00am"));
        assertNull(DateTimeParser.parseTime("invalid-time"));
    }
}
