package com.example.tradingapp.Repository;

import com.example.tradingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
