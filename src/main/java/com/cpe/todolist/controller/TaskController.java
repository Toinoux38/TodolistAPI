package com.cpe.todolist.controller;

import com.cpe.todolist.model.Task;
import com.cpe.todolist.model.User;
import com.cpe.todolist.repository.UserRepository;
import com.cpe.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserRepository userRepository;

    @GetMapping
    public List<Task> getAllTasks(@RequestHeader("User-Id") Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return taskService.getTasks(user);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id, @RequestHeader("User-Id") Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return taskService.getTask(id, user);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task, @RequestHeader("User-Id") Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return taskService.createTask(task, user);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task, @RequestHeader("User-Id") Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return taskService.updateTask(id, task, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, @RequestHeader("User-Id") Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        taskService.deleteTask(id, user);
        return ResponseEntity.ok().build();
    }
} 