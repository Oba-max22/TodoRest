package com.sq018.todorest.controller;

import com.sq018.todorest.model.Todo;
import com.sq018.todorest.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TodoController {
    public static final String TODO_DELETED_SUCCESSFULLY = "Todo deleted successfully!";
    private final TodoService todoService;

    @GetMapping("/")
    public String welcome() {
        return "Hello, World";
    }

    @PostMapping("/todos")
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo request) {
        Todo response = todoService.createTodo(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getTodos() {
        List<Todo> todoList = todoService.getTodos();
        return ResponseEntity.ok(todoList);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getSingleTodo(@PathVariable("id") Long id) {
        Todo todoResponse = todoService.getTodoById(id);
        return new ResponseEntity<>(todoResponse, HttpStatus.OK);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") Long id, @RequestBody Todo request) {
        Todo todoResponse = todoService.updateTodoById(id, request);
        return new ResponseEntity<>(todoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/todos")
    public ResponseEntity<?> deleteTodo(@RequestParam(name = "id", required = false) Long id) {
        todoService.deleteTodoById(id);
        return ResponseEntity.ok(TODO_DELETED_SUCCESSFULLY);
    }

    @GetMapping("/todos/page")
    public ResponseEntity<Page<Todo>> getAllTodosPage(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                               @RequestParam(name = "size", required = false, defaultValue = "5") int size) {
        Page<Todo> todosPage = todoService.getTodosPage(page, size);
        return ResponseEntity.ok(new PageImpl<>(todosPage.getContent(), todosPage.getPageable(), todosPage.getTotalElements()));
    }

    @GetMapping("/todos/search")
    public ResponseEntity<List<Todo>> searchTodos(@RequestParam(name = "title", required = false) String title,
                                                  @RequestParam(name = "description", required = false) String description,
                                                  @RequestParam(name = "completed", required = false) boolean completed) {
        List<Todo> todoList = todoService.searchTodos(title, description, completed);
        return ResponseEntity.ok(todoList);
    }
}
