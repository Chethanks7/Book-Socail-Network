package com.Book_social_Network.security;

import com.Book_social_Network.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Indicates that this class is a service component in the Spring context.
@RequiredArgsConstructor // Generates a constructor for dependency injection of required arguments.
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository; // Repository for accessing user data.

    /**
     * Loads a user by their email.
     *
     * @param userEmail the email of the user to load.
     * @return UserDetails object containing user information.
     * @throws UsernameNotFoundException if no user is found with the given email.
     */
    @Override
    @Transactional // Indicates that this method should be wrapped in a transaction.
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        // Attempts to find the user by their email.
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userEmail: " + userEmail));
    }
}
