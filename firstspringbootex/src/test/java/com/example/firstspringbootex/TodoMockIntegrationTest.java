package com.example.firstspringbootex;

import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.resttestclient.TestRestTemplate;
//import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import com.example.firstspringbootex.Service.TodoService;

import static org.assertj.core.api.Assertions.assertThat;//verify respose & payload

// 1. Boots up entire app with a real random port server
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoMockIntegrationTest {

    // sticky notes,implicitly injects random port
    @LocalServerPort
    private int port;

    // helping client(user) to fire real HTTP request over n/w
    // @Autowired
    // private TestRestTemplate restTemplate;

    // Mocks out a heavy service bean so we isolate our web integration flow
    @MockitoBean
    private TodoService todoService;

    // Dynamic Property Injector before intilizing Spring bean(Container)
    @DynamicPropertySource
    static void ConfigureProperties(DynamicPropertyRegistry registry) {
        registry.add("My dynamic test properties:", () -> "Active");
    }

    // Actual test that acts as a handler for
    // LocalserverPort(Url),RestTemplate(geting json res),verify payload structure
    @Test
    public void TodogetMockIntegrationTest() {

        // Builds live url for random port injecting @LocalServerPort
        String url = "http://localhost:" + port + "/todo";

        // Fire a real HTTP GET request using TestRestTemplate
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        // Verify the response code or payload structure
        assertThat(response).isNotNull();// check import static plz
    }

}
