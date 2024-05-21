package com.application.todoapp.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import com.application.todoapp.entity.Todo;

public class TodoRepositoryTest {

    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        todoRepository = new TodoRepository();
        Todo newTodo = new Todo();
    	newTodo.setTask("Click to edit task name");
    	newTodo.setIsDone(true);
    	todoRepository.save(newTodo);
    }

    @Test
    void testSaveAddsNewTodoCorrectly() {
    	Todo newTodo = new Todo();
    	newTodo.setTask("New Task");
    	newTodo.setIsDone(true);
    	
    	Todo savedTodo = todoRepository.save(newTodo);
    	assertNotNull(savedTodo.getId(), "El todo guardado debe tener un ID");
    	assertEquals(2, todoRepository.fetchAllTodos().size(), "Ahora debe haber 2 todos en la lista");
    	
    	
    	Todo lastTodo = todoRepository.fetchAllTodos().get(1);
    	assertEquals("New Task", lastTodo.getTask(), "La tarea del nuevo todo debe coincidir");
    	assertTrue(lastTodo.getIsDone(), "El estado de completado debe ser verdadero");
    }
    @Test
    void testFetchAllTodosInitiallyContainsOneItem() {
        List<Todo> todos = todoRepository.fetchAllTodos();
        assertFalse(todos.isEmpty(), "La lista no debe estar vacía inicialmente");
        assertEquals(1, todos.size(), "Debe contener exactamente 1 todo inicialmente");
        assertEquals("Click to edit task name", todos.get(0).getTask(), "El nombre de la tarea inicial debe coincidir");
    }


    @Test
    void testDeleteRemovesCorrectTodo() {
        Todo newTodo = new Todo();
        newTodo.setTask("Task to be deleted");
        newTodo.setIsDone(false);
        todoRepository.save(newTodo);

        assertEquals(2, todoRepository.fetchAllTodos().size(), "Debería haber 2 todos antes de eliminar");

        todoRepository.delete(newTodo.getId()); 
        assertEquals(1, todoRepository.fetchAllTodos().size(), "Debería haber 1 todo después de eliminar");
        assertEquals("Click to edit task name", todoRepository.fetchAllTodos().get(0).getTask(), "El todo restante debe ser el inicial");
    }
}

