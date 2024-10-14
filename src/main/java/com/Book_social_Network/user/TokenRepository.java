package com.Book_social_Network.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// TokenRepository interface for accessing Token entities in the database.
public interface TokenRepository extends JpaRepository<Token, Integer> {

    // Finds a Token entity by its token string.
    Optional<Token> findByToken(String token);
}
