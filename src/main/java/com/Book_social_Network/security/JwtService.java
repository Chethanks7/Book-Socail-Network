package com.Book_social_Network.security;

import org.springframework.stereotype.Service;

@Service
public class JwtService {


    public String generateToken(UserDetailsServiceImpl userDetails) {


        return null;
    }


    public String extractUsername(String jwt) {


        return jwt.substring(7);
    }
}
