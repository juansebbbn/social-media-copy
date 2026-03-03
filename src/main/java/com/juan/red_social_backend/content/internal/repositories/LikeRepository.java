package com.juan.red_social_backend.content.internal.repositories;

import com.juan.red_social_backend.content.internal.entites.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {
    List<Like> findBypostId(UUID postId);
}
