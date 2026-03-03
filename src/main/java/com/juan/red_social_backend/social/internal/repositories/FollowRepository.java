package com.juan.red_social_backend.social.internal.repositories;

import com.juan.red_social_backend.social.internal.entities.Follow;
import com.juan.red_social_backend.social.internal.entities.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    List<Follow> findById_FollowerId(UUID followerId);
    List<Follow> findById_FollowingId(UUID followingId);
    long countById_FollowerId(UUID followerId);
    long countById_FollowingId(UUID followingId);
}
