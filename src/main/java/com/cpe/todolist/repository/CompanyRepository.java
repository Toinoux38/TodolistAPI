package com.cpe.todolist.repository;

import com.cpe.todolist.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
} 