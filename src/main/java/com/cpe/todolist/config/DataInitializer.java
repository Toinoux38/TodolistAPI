package com.cpe.todolist.config;

import com.cpe.todolist.model.Company;
import com.cpe.todolist.model.Task;
import com.cpe.todolist.model.User;
import com.cpe.todolist.model.UserRole;
import com.cpe.todolist.repository.CompanyRepository;
import com.cpe.todolist.repository.TaskRepository;
import com.cpe.todolist.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(CompanyRepository companyRepository,
                             UserRepository userRepository,
                             TaskRepository taskRepository) {
        return args -> {
            // Create companies
            Company company1 = new Company();
            company1.setName("Tech Corp");
            companyRepository.save(company1);

            Company company2 = new Company();
            company2.setName("Digital Solutions");
            companyRepository.save(company2);

            // Create users
            User standardUser = new User();
            standardUser.setUsername("john.doe");
            standardUser.setRole(UserRole.STANDARD_USER);
            standardUser.setCompany(company1);
            userRepository.save(standardUser);

            User companyAdmin = new User();
            companyAdmin.setUsername("jane.smith");
            companyAdmin.setRole(UserRole.COMPANY_ADMIN);
            companyAdmin.setCompany(company1);
            userRepository.save(companyAdmin);

            User superUser = new User();
            superUser.setUsername("admin");
            superUser.setRole(UserRole.SUPER_USER);
            userRepository.save(superUser);

            // Create tasks
            Task task1 = new Task();
            task1.setTitle("Complete project documentation");
            task1.setDescription("Write technical documentation for the new feature");
            task1.setCompleted(false);
            task1.setUser(standardUser);
            taskRepository.save(task1);

            Task task2 = new Task();
            task2.setTitle("Review code");
            task2.setDescription("Review pull requests for the team");
            task2.setCompleted(true);
            task2.setUser(companyAdmin);
            taskRepository.save(task2);
        };
    }
} 