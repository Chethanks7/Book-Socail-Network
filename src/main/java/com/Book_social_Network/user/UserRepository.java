package com.Book_social_Network.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
     // Custom method to find a user by their email address
     Optional<User> findByEmail(String email);
}
