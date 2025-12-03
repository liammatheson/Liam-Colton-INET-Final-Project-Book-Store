package com.example.Liam_Colton_Bookstore.repository;

import com.example.Liam_Colton_Bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
