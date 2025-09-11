package gertrude.task;

import org.junit.jupiter.api.Test;

import gertrude.exceptions.SaveFileBadLineException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void fromFileFormat_validTodoFormat_shouldReturnTodo() throws SaveFileBadLineException {
        String savedTodo = "T | 1 | Read book";
        Task task = Task.fromFileFormat(savedTodo);

        assertNotNull(task);
        assertTrue(task instanceof Todo);
        assertEquals("Read book", task.getTitle());
        assertTrue(((CompletableTask) task).isCompleted());
    }

    @Test
    void fromFileFormat_validDeadlineFormat_shouldReturnDeadline() throws SaveFileBadLineException {
        String savedDeadline = "D | 0 | Submit assignment | 2023-10-15T00:00";
        Task task = Task.fromFileFormat(savedDeadline);

        assertNotNull(task);
        assertTrue(task instanceof Deadline);
        assertEquals("Submit assignment", task.getTitle());
        assertFalse(((CompletableTask) task).isCompleted());
        assertEquals(LocalDateTime.of(2023, 10, 15, 0, 0), ((Deadline) task).getDeadline());
    }

    @Test
    void fromFileFormat_validEventFormat_shouldReturnEvent() throws SaveFileBadLineException {
        String savedEvent = "E | 1 | Team meeting | 2023-10-15T10:00 | 2023-10-15T12:00";
        Task task = Task.fromFileFormat(savedEvent);

        assertNotNull(task);
        assertTrue(task instanceof Event);
        assertEquals("Team meeting", task.getTitle());
        assertTrue(((CompletableTask) task).isCompleted());
        assertEquals(LocalDateTime.of(2023, 10, 15, 10, 0), ((Event) task).getStart());
        assertEquals(LocalDateTime.of(2023, 10, 15, 12, 0), ((Event) task).getEnd());
    }

    @Test
    void fromFileFormat_invalidFormat_shouldThrowError() throws SaveFileBadLineException {
        String invalidFormat = "X | 1 | Invalid task";
        assertThrows(SaveFileBadLineException.class, () -> {
            Task.fromFileFormat(invalidFormat);
        });
    }

    @Test
    void fromFileFormat_corruptedData_shouldThrowError() throws SaveFileBadLineException {
        String corruptedData = "D | 1 | Missing deadline";
        assertThrows(SaveFileBadLineException.class, () -> {
            Task.fromFileFormat(corruptedData);
        });
    }
}
