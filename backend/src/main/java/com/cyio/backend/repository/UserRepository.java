package com.cyio.backend.repository;

import com.cyio.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//communication between backend and server
@Repository
public interface UserRepository extends JpaRepository<User,String> {
        Optional<User> findByUserName(String userName);
        Boolean existsByEmail(String email);

        @Transactional
         int deleteUserByUserNameOrUserid(String userName, String Userid);

        @Modifying
        @Transactional
        @Query("update User u set u.admin = ?1 where u.userid = ?2 or u.userName = ?3")
        void updateAdmin(boolean admin, String userName, String Userid);

        List<User> findUserByUserNameOrUserid(String userName, String Userid);

        List<User> findAll();

}
