package com.cyio.backend.repository;

import com.cyio.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//communication between backend and server

public interface UserRepository extends JpaRepository<User,String> {
        Optional<User> findByEmail(String email);

        Boolean existsByEmail(String email);
}
