package com.cyio.backend.repository;

import com.cyio.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
        Optional<User> findByEmail(String email);

        Boolean existsByEmail(String email);
}
