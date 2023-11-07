package com.sq018.todorest.service;

import com.sq018.todorest.model.Todo;
import com.sq018.todorest.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;


    public Todo createTodo(Todo request) {
        return todoRepository.save(request);
    }
}

