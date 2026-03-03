package com.juan.red_social_backend.identity.internal.repositories;

import com.juan.red_social_backend.identity.internal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByemail(String email);
}
