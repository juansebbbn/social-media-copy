package com.juan.red_social_backend.social.internal.repositories;

import com.juan.red_social_backend.social.internal.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    @Query("SELECT p FROM Profile p WHERE p.userId = :id")
    Profile findProfileById_user(@Param("id") UUID id);

    Profile findProfileByUsername(String usernameFollowed);
}
