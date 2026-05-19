package com.Diamondbarberdhopapp.backend.repository;

import com.Diamondbarberdhopapp.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
