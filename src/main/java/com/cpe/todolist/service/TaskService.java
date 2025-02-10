package com.cpe.todolist.service;

import com.cpe.todolist.exception.TaskNotFoundException;
import com.cpe.todolist.exception.UnauthorizedAccessException;
import com.cpe.todolist.model.Task;
import com.cpe.todolist.model.User;
import com.cpe.todolist.model.UserRole;
import com.cpe.todolist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getTasks(User currentUser) {
        return switch (currentUser.getRole()) {
            case SUPER_USER -> taskRepository.findAll();
            case COMPANY_ADMIN -> taskRepository.findByUserCompany(currentUser.getCompany());
            case STANDARD_USER -> taskRepository.findByUser(currentUser);
            default -> throw new UnauthorizedAccessException("Invalid user role");
        };
    }

    public Task getTask(Long id, User currentUser) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (canAccessTask(currentUser, task)) {
            return task;
        }
        throw new UnauthorizedAccessException("User not authorized to access this task");
    }

    public Task createTask(Task task, User currentUser) {
        task.setUser(currentUser);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails, User currentUser) {
        Task task = getTask(id, currentUser);
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id, User currentUser) {
        Task task = getTask(id, currentUser);
        taskRepository.delete(task);
    }

    private boolean canAccessTask(User user, Task task) {
        return switch (user.getRole()) {
            case SUPER_USER -> true;
            case COMPANY_ADMIN -> task.getUser().getCompany().getId().equals(user.getCompany().getId());
            case STANDARD_USER -> task.getUser().getId().equals(user.getId());
        };
    }
} 