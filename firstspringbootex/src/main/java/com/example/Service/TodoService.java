package com.example.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.Entity.Todo;

@Service
public class TodoService {

    // In-memory storage
    private final Map<Long, Todo> todoStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Get all Todos
    public List<Todo> findAll() {
        return new ArrayList<>(todoStore.values());
    }

    // Get Todo by ID
    public Optional<Todo> findById(Long id) {
        return Optional.ofNullable(todoStore.get(id));
    }

    // Create a new Todo
    public Todo create(Todo todo) {
        Long newId = idGenerator.getAndIncrement();
        todo.setid(newId);
        todoStore.put(newId, todo);
        return todo;
    }

    // Update an existing Todo
    public Optional<Todo> update(Long id, Todo updatedTodo) {
        if (todoStore.containsKey(id)) {
            updatedTodo.setid(id);// Ensure the ID remains the same
            todoStore.put(id, updatedTodo);
            return Optional.of(updatedTodo);
        }
        return Optional.empty();
    }

    // Delete a Todo
    public boolean delete(Long id) {
        return todoStore.remove(id) != null;
    }
}