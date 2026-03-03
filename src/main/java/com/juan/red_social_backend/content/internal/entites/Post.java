package com.juan.red_social_backend.content.internal.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "profile_id", nullable = false)
    private UUID profileId; // Referencia al perfil, pero solo el ID

    @Lob // Esto le dice a Hibernate que acepte el CLOB de H2
    @Column(name = "content", nullable = false)
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();
}