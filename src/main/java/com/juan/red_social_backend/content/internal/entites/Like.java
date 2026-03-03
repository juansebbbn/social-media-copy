package com.juan.red_social_backend.content.internal.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"profile_id", "post_id"})
})
@Getter @Setter
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "profile_id", nullable = false)
    private UUID profileId;

    @Column(name = "post_id", nullable = false)
    private UUID postId;

    private LocalDateTime createdAt = LocalDateTime.now();
}