package com.example.firstspringbootex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;//fake obj
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;//poke url & verify respose

@SpringBootTest // wakes up entire appln
@AutoConfigureMockMvc // gives priviledges to test entire appln by providing mockmvc(plugged simulated
                      // brower) via. HTTP
public class TodoControllerIntegrationTest {

    private MockMvc mockMvc;// simulator browser to poke all url and verify response

    @Autowired
    public TodoControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testGetAllTodos() throws Exception {
        // Simulating a GET request to http://localhost:8080/todo
        mockMvc.perform(get("/todo"))
                .andExpect(status().isOk()); // Expecting HTTP 200 OK
    }
}