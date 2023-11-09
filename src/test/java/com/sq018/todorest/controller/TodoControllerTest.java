package com.sq018.todorest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sq018.todorest.model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class TodoControllerTest {

    private Todo todo;
    private Todo secondTodo;
    private Todo thirdTodo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @BeforeEach
    void setup() {
        todo = Todo.builder().title("Integration tests").description("Write integration Tests")
                .completed(true).build();

        secondTodo = Todo.builder().title("Second todo").description("Second todo description")
                .completed(false).build();

        thirdTodo = Todo.builder().title("Third todo").description("Third todo description")
                .completed(false).build();
    }

    @Test
    void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, World"));
    }

    @Test
    void createTodoAPI() throws Exception {
        this.mockMvc.perform(post("/todos")
                .content(asJsonString(todo))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Integration tests"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Write integration Tests"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.completed").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt").exists());
    }

    @Test
    public void getAllTodosAPI() throws Exception
    {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/todos")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").doesNotExist());
    }

//    @Test
//    public void getTodoByIdAPI() throws Exception
//    {
//        this.mockMvc.perform( MockMvcRequestBuilders
//                        .get("/todos/{id}", 1L)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
//    }

//    @Test
//    void updateTodoAPI() throws Exception {
//        Todo request = Todo.builder()
//                .id(1L).title("New todo").description("New todo description")
//                .completed(false).build();
//
//        this.mockMvc.perform(put("/todos/{id}", 1L)
//                        .content(asJsonString(request))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("New Todo"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("New todo description"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt").exists());
//    }

//    @Test
//    public void deleteTodoAPI() throws Exception
//    {
//        this.mockMvc.perform( MockMvcRequestBuilders.delete("/todos").param("id", String.valueOf(1L)))
//                .andExpect(status().isOk());
//    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
