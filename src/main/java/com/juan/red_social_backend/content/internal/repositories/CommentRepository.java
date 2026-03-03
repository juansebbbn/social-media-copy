package com.juan.red_social_backend.content.internal.repositories;

import com.juan.red_social_backend.content.internal.entites.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findBypostId(UUID idPost);
}
