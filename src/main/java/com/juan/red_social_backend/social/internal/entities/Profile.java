package com.juan.red_social_backend.social.internal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId; // logical pointer to user

    @Column(nullable = false, unique = true)
    private String username;

    private String bio;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    private boolean private_profile;

    private LocalDateTime createdAt = LocalDateTime.now();
}