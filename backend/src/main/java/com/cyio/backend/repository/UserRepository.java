package com.cyio.backend.repository;

import com.cyio.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//communication between backend and server
@Repository
public interface UserRepository extends JpaRepository<User,String> {
        Optional<User> findByUserName(String userName);
        Boolean existsByEmail(String email);

        Boolean deleteUserByUserNameOrUserid(String userName, String Userid);

        List<User> findByUserNameOrUserid(String userName, String Userid);
}
