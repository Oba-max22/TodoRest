package com.sq018.todorest.repository;

import com.sq018.todorest.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByTitleContaining(String title);
    List<Todo> findAllByDescriptionContaining(String description);
    List<Todo> findAllByCompleted(boolean completed);
}
