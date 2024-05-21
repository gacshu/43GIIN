package com.application.todoapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.application.todoapp.entity.Todo;
import com.application.todoapp.service.TodoService;

import java.util.List;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    void testFetchAllTodos() throws Exception {
        
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("Test Task");
        todo.setIsDone(false);
        given(todoService.fetchAllTodos()).willReturn(List.of(todo));

        
        mockMvc.perform(get("/api/todoItems"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].task").value("Test Task"))
        .andExpect(jsonPath("$[0].isDone").value(false));
    }

    @Test
    void testCreateNewTodoItem() throws Exception {
        Todo newTodo = new Todo();
        newTodo.setId(1);
        newTodo.setTask("New Task");
        newTodo.setIsDone(true);
        given(todoService.createNewTodoItem()).willReturn(newTodo);

        mockMvc.perform(post("/api/todoItems")
                .contentType(MediaType.APPLICATION_JSON))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.id").value(1))
       .andExpect(jsonPath("$.task").value("New Task"))
       .andExpect(jsonPath("$.isDone").value(true));
    }

    @Test
    void testUpdateTodoItem() throws Exception {
        Todo updatedTodo = new Todo();
        updatedTodo.setId(1);
        updatedTodo.setTask("Updated Task");
        updatedTodo.setIsDone(true);
        given(todoService.updateTodoItem(eq(1), any(Todo.class))).willReturn(updatedTodo);

        mockMvc.perform(put("/api/todoItems/{id}", 1)
                .content("{\"task\":\"Updated Task\",\"isDone\":true}")
                .contentType(MediaType.APPLICATION_JSON))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.id").value(1))
       .andExpect(jsonPath("$.task").value("Updated Task"))
       .andExpect(jsonPath("$.isDone").value(true));
    }

    @Test
    void testDeleteTodoItem() throws Exception {
        doNothing().when(todoService).deleteTodoItem(1);

        mockMvc.perform(delete("/api/todoItems/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(content().string("ok"));
    }
}



