package com.example.taskapi.controller;

import com.example.taskapi.entity.Task;
import com.example.taskapi.entity.User;
import com.example.taskapi.repository.TaskRepository;
import com.example.taskapi.repository.UserRepository;
import com.example.taskapi.config.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository, JwtUtil jwtUtil) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<?> getTasks(@RequestHeader("Authorization") String authHeader) {
        String email = jwtUtil.extractUsername(authHeader.substring(7));
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return ResponseEntity.status(403).build();
        List<Task> tasks = taskRepository.findByUser(userOpt.get());
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestHeader("Authorization") String authHeader, @RequestBody Task task) {
        String email = jwtUtil.extractUsername(authHeader.substring(7));
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return ResponseEntity.status(403).build();
        task.setUser(userOpt.get());
        return ResponseEntity.ok(taskRepository.save(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestHeader("Authorization") String authHeader, @RequestBody Task updatedTask) {
        String email = jwtUtil.extractUsername(authHeader.substring(7));
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return ResponseEntity.status(403).build();
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isEmpty() || !existingTask.get().getUser().equals(userOpt.get())) {
            return ResponseEntity.status(403).build();
        }
        existingTask.get().setStatus(updatedTask.getStatus());
        return ResponseEntity.ok(taskRepository.save(existingTask.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        String email = jwtUtil.extractUsername(authHeader.substring(7));
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return ResponseEntity.status(403).build();
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isEmpty() || !existingTask.get().getUser().equals(userOpt.get())) {
            return ResponseEntity.status(403).build();
        }
        taskRepository.delete(existingTask.get());
        return ResponseEntity.ok().build();
    }
}
