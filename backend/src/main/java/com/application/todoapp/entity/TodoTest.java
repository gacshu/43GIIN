package com.application.todoapp.entity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TodoTest {

    @Test
    void testIdProperty() {
        Todo todo = new Todo();
        todo.setId(1);
        assertEquals(1, todo.getId(), "El ID debe ser igual a 1");
    }

    @Test
    void testTaskProperty() {
        Todo todo = new Todo();
        String expectedTask = "Complete unit tests";
        todo.setTask(expectedTask);
        assertEquals(expectedTask, todo.getTask(), "La tarea debe ser 'Complete unit tests'");
    }

    @Test
    void testIsDoneProperty() {
        Todo todo = new Todo();
        todo.setIsDone(true);
        assertEquals(true, todo.getIsDone(), "isDone debe ser verdadero");
    }
}