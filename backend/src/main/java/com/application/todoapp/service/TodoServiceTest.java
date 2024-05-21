package com.application.todoapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.application.todoapp.entity.Todo;
import com.application.todoapp.repository.TodoRepository;

public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchAllTodosInitiallyContainsOneItem() {
        Todo initialTodo = new Todo();
        initialTodo.setId(1);
        initialTodo.setTask("Click to edit task name");
        initialTodo.setIsDone(false);
        List<Todo> expectedTodos = Arrays.asList(initialTodo);

        when(todoRepository.fetchAllTodos()).thenReturn(expectedTodos);

        List<Todo> todos = todoService.fetchAllTodos();
        assertFalse(todos.isEmpty(), "La lista no debe estar vacía inicialmente");
        assertEquals(1, todos.size(), "Debe contener exactamente 1 todo inicialmente");
        assertEquals("Click to edit task name", todos.get(0).getTask(), "El nombre de la tarea inicial debe coincidir");
    }
    @Test
    void testCreateNewTodoItem() {
        Todo savedTodo = new Todo();
        savedTodo.setId(2); 
        savedTodo.setIsDone(false);

        when(todoRepository.save(any(Todo.class))).thenReturn(savedTodo);

        Todo newTodo = todoService.createNewTodoItem();

        assertNotNull(newTodo, "El nuevo todo no debe ser nulo");
        assertEquals("Click to edit task name", newTodo.getTask(), "La tarea debe tener el nombre predeterminado");
        assertFalse(newTodo.getIsDone(), "El estado de completado debe ser falso inicialmente");
        verify(todoRepository).save(any(Todo.class)); 
    }
    @Test
    void testUpdateTodoItemWhenPresent() {
        Todo existingTodo = new Todo();
        existingTodo.setId(1);
        existingTodo.setTask("Original Task");
        existingTodo.setIsDone(false);
        Todo updatedInfo = new Todo();
        updatedInfo.setTask("Updated Task");
        updatedInfo.setIsDone(true);

        when(todoRepository.fetchAllTodos()).thenReturn(Arrays.asList(existingTodo));

        Todo updatedTodo = todoService.updateTodoItem(1, updatedInfo);

        assertNotNull(updatedTodo, "El todo actualizado no debe ser nulo");
        assertEquals("Updated Task", updatedTodo.getTask(), "La tarea debe ser actualizada correctamente");
        assertTrue(updatedTodo.getIsDone(), "El estado de completado debe ser verdadero");
    }

    @Test
    void testUpdateTodoItemWhenNotPresent() {
        when(todoRepository.fetchAllTodos()).thenReturn(Arrays.asList());

        Todo updatedTodo = todoService.updateTodoItem(1, new Todo());

        assertNull(updatedTodo, "Debe retornar nulo si el todo no existe");
    }
    @Test
    void testDeleteTodoItem() {
        Integer todoId = 1;  

        todoService.deleteTodoItem(todoId);
        
        verify(todoRepository).delete(todoId);
    }
}

