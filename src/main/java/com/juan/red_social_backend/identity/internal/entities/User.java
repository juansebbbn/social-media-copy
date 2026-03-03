package com.juan.red_social_backend.identity.internal.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email can not be null")
    @Email(message = "Email format not valid")
    private String email;

    @Column(nullable = false)
    private String password;
}