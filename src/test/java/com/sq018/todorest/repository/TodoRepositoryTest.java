package com.sq018.todorest.repository;

import com.sq018.todorest.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TodoRepository todoRepositoryUnderTest;

    @Test
    public void should_find_no_todos_if_repository_is_empty() {
        List<Todo> Todos = todoRepositoryUnderTest.findAll();

        assertThat(Todos).isEmpty();
    }

    @Test
    public void should_persist_a_todo() {
        Todo todo = new Todo();
        todo.setTitle("JPA tests");
        todo.setDescription("Write JPA Tests");
        todo.setCompleted(true);
        Todo savedTodo = todoRepositoryUnderTest.save(todo);

        assertThat(savedTodo).hasFieldOrPropertyWithValue("title", "JPA tests");
        assertThat(savedTodo).hasFieldOrPropertyWithValue("description", "Write JPA Tests");
        assertThat(savedTodo).hasFieldOrPropertyWithValue("completed", true);
    }

    @Test
    public void should_find_all_todos() {
        Todo todo = Todo.builder().title("Integration tests").description("Write integration Tests")
                .completed(true).build();
        entityManager.persist(todo);

        Todo secondTodo = Todo.builder().title("Second todo").description("Second todo description")
                .completed(false).build();
        entityManager.persist(secondTodo);

        Todo thirdTodo = Todo.builder().title("Third todo").description("Third todo description")
                .completed(false).build();
        entityManager.persist(thirdTodo);

        List<Todo> todoList = todoRepositoryUnderTest.findAll();

        assertThat(todoList).hasSize(3).contains(todo, secondTodo, thirdTodo);
    }

    @Test
    public void should_find_todo_by_id() {
        Todo todo1 = Todo.builder().title("Integration tests").description("Write integration Tests")
                .completed(true).build();

        entityManager.persist(todo1);

        Todo foundTodo = todoRepositoryUnderTest.findById(todo1.getId()).get();

        assertThat(foundTodo).isEqualTo(todo1);
    }


    @Test
    public void should_find_todos_by_title_containing_string() {
        Todo todo1 = Todo.builder().title("Integration tests").description("Write integration Tests")
                .completed(true).build();
        entityManager.persist(todo1);

        Todo todo2 = Todo.builder().title("Integration tests 2").description("Write integration Tests 2")
                .completed(true).build();
        entityManager.persist(todo2);

        List<Todo> todoList = todoRepositoryUnderTest.findAllByTitleContaining("tests");

        assertThat(todoList).hasSize(2).contains(todo1, todo2);
    }

    @Test
    public void should_update_Todo_by_id() {
        Todo todo1 = Todo.builder().title("todo#1").description("Desc#1")
                .completed(true).build();

        entityManager.persist(todo1);

        Todo todo2 = Todo.builder().title("todo#2").description("Desc#2")
                .completed(true).build();
        entityManager.persist(todo2);

        Todo updatedtodo = Todo.builder().title("updated todo#1").description("updated Desc#1")
                .completed(true).build();

        Todo todo = todoRepositoryUnderTest.findById(todo2.getId()).get();
        todo.setTitle(updatedtodo.getTitle());
        todo.setDescription(updatedtodo.getDescription());
        todo.setCompleted(updatedtodo.isCompleted());
        todoRepositoryUnderTest.save(todo);

        Todo checkTodo = todoRepositoryUnderTest.findById(todo2.getId()).get();

        assertThat(checkTodo.getId()).isEqualTo(todo2.getId());
        assertThat(checkTodo.getTitle()).isEqualTo(updatedtodo.getTitle());
        assertThat(checkTodo.getDescription()).isEqualTo(updatedtodo.getDescription());
        assertThat(checkTodo.isCompleted()).isEqualTo(updatedtodo.isCompleted());
    }

    @Test
    public void should_delete_Todo_by_id() {
        Todo todo1 = Todo.builder().title("todo#1").description("Desc#1")
                .completed(true).build();

        entityManager.persist(todo1);

        Todo todo2 = Todo.builder().title("todo#2").description("Desc#2")
                .completed(true).build();
        entityManager.persist(todo2);

        Todo todo3 = Todo.builder().title("todo#3").description("Desc#3")
                .completed(true).build();
        entityManager.persist(todo3);

        todoRepositoryUnderTest.deleteById(todo2.getId());

        List<Todo> Todos = todoRepositoryUnderTest.findAll();

        assertThat(Todos).hasSize(2).contains(todo1, todo3);
    }

}
