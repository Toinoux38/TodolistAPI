package com.cpe.todolist.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    
    private String username;
    
    @Enumerated(EnumType.STRING)
    private UserRole role;
    
    @ManyToOne
    private Company company;
} 