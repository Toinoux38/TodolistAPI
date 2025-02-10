package com.cpe.todolist.service;

import com.cpe.todolist.model.Company;
import com.cpe.todolist.model.Task;
import com.cpe.todolist.model.User;
import com.cpe.todolist.model.UserRole;
import com.cpe.todolist.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private User standardUser;
    private User companyAdmin;
    private User superUser;
    private Company company;
    private Task task;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setId(1L);
        company.setName("Test Company");

        standardUser = new User();
        standardUser.setId(1L);
        standardUser.setRole(UserRole.STANDARD_USER);
        standardUser.setCompany(company);

        companyAdmin = new User();
        companyAdmin.setId(2L);
        companyAdmin.setRole(UserRole.COMPANY_ADMIN);
        companyAdmin.setCompany(company);

        superUser = new User();
        superUser.setId(3L);
        superUser.setRole(UserRole.SUPER_USER);

        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setUser(standardUser);
    }

    @Test
    void whenSuperUser_thenGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task));
        
        List<Task> tasks = taskService.getTasks(superUser);
        
        assertEquals(1, tasks.size());
        verify(taskRepository).findAll();
    }

    @Test
    void whenCompanyAdmin_thenGetCompanyTasks() {
        when(taskRepository.findByUserCompany(company)).thenReturn(Arrays.asList(task));
        
        List<Task> tasks = taskService.getTasks(companyAdmin);
        
        assertEquals(1, tasks.size());
        verify(taskRepository).findByUserCompany(company);
    }

    @Test
    void whenStandardUser_thenGetUserTasks() {
        when(taskRepository.findByUser(standardUser)).thenReturn(Arrays.asList(task));
        
        List<Task> tasks = taskService.getTasks(standardUser);
        
        assertEquals(1, tasks.size());
        verify(taskRepository).findByUser(standardUser);
    }
} 