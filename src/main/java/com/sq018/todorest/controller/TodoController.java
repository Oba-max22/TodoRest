package com.sq018.todorest.controller;

import com.sq018.todorest.model.Todo;
import com.sq018.todorest.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;

    // todo: endpoint to create todos
    @PostMapping("/todos")
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo request) {
        Todo response = todoService.createTodo(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // todo: endpoint to get all todos
    // todo: endpoint to get single todo
    // todo: endpoint to update todo
    // todo: endpoint to delete todo
}
