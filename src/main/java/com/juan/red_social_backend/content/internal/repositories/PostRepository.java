package com.juan.red_social_backend.content.internal.repositories;

import com.juan.red_social_backend.content.internal.entites.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByprofileId(UUID id);
}
