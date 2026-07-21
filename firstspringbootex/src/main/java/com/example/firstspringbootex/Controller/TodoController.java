package com.example.firstspringbootex.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.firstspringbootex.Entity.Todo;
import com.example.firstspringbootex.Service.TodoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    // Constructor Injection
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // Requirement 2: GET /todo (Returns list of todos as JSON)
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.findAll());
    }

    // Requirement 3: GET /todo/{id} (Returns todo by ID or 404)
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return todoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Requirement 5: POST /todo (Accepts JSON payload {title} and creates new todo)
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        Todo createdTodo = todoService.create(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    // Requirement 4: PUT /todo/{id} (Updates existing todo, returns 404 if not
    // found)
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        return todoService.update(id, todo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Requirement 6: DELETE /todo/{id} (Deletes todo by ID, returns 404 if not
    // found)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        if (todoService.delete(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content for successful deletion
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
