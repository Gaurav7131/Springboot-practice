package com.example.firstspringbootex;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.firstspringbootex.Controller.TodoController;
import com.example.firstspringbootex.Service.TodoService;

public class TodoControllerTest {

    private MockMvc mockMvc; // Simulated browser tool

    @Mock
    private TodoService todoService; // Fake mock service (Chef)

    @InjectMocks
    private TodoController todoController; // Real controller with the fake service injected inside it

    @BeforeEach
    public void setup() {
        // 1. Opens all Mockito annotations (@Mock and @InjectMocks)
        MockitoAnnotations.openMocks(this);

        // 2. Manually builds standalone MockMvc without booting up the heavy Spring
        // container
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
    }

    @Test
    public void testGetAllTodos() throws Exception {
        // Simulating a GET request to /todo and checking for HTTP 200 OK
        mockMvc.perform(get("/todo"))
                .andExpect(status().isOk());
    }
}