package com.cpe.todolist.repository;

import com.cpe.todolist.model.Company;
import com.cpe.todolist.model.Task;
import com.cpe.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    List<Task> findByUserCompany(Company company);
} 