package com.juan.red_social_backend.social.internal.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "follows")
@Getter
@Setter
public class Follow {
    @EmbeddedId
    private FollowId id;
    private LocalDateTime createdAt = LocalDateTime.now();
}

