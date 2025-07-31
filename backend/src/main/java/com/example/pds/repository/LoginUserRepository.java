package com.example.pds.repository;

import com.example.pds.model.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, String> {
    // user_id se user ko find karne ke liye method
    Optional<LoginUser> findByUserId(String userId);
}
