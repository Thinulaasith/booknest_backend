package com.booknest.entities;

import com.booknest.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
}