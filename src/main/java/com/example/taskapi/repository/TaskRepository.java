package com.example.taskapi.repository;

import com.example.taskapi.entity.Task;
import com.example.taskapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
