package com.sq018.todorest.service;

import com.sq018.todorest.exception.ResourceNotFoundException;
import com.sq018.todorest.model.Todo;
import com.sq018.todorest.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoService {

    public static final String INVALID_ID = "Invalid ID";
    private final TodoRepository todoRepository;


    public Todo createTodo(Todo request) {
        return todoRepository.save(request);
    }

    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(INVALID_ID));
    }

    public Todo updateTodoById(Long id, Todo request) {
        Todo foundTodo = getTodoById(id);

        foundTodo.setTitle(request.getTitle());
        foundTodo.setDescription(request.getDescription());
        foundTodo.setCompleted(request.isCompleted());

        return todoRepository.save(foundTodo);
    }

    public void deleteTodoById(Long id) {
        Todo foundTodo = getTodoById(id);
        todoRepository.delete(foundTodo);
    }

    public Page<Todo> getTodosPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return todoRepository.findAll(pageable);
    }

    public List<Todo> searchTodos(String title, String description, boolean completed) {
        List<Todo> result = new ArrayList<>();
        if (title != null && !title.isEmpty()) {
            result.addAll(todoRepository.findAllByTitleContaining(title));
        } else if (description != null && !description.isEmpty()) {
            result.addAll(todoRepository.findAllByDescriptionContaining(description));
        } else {
            result.addAll(todoRepository.findAllByCompleted(completed));
        }
        return result;
    }
}

