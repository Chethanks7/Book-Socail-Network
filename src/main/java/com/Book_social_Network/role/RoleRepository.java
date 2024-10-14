package com.Book_social_Network.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository interface for managing Role entities.
public interface RoleRepository extends JpaRepository<Role, Integer> {

    // Method to find a Role by its name.
    Optional<Role> findByName(String role);
}
