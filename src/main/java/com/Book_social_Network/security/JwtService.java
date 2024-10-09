package com.Book_social_Network.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.secrete-key}")
    private String secreteKey;

    public String generateToken(UserDetailsServiceImpl userDetails) {


        return null;
    }

    public String extractUsername(String token) {

        return null;
    }

    public String generateToken(
            UserDetails userDetails
    ) {
        return generateToken(new HashMap<String, Object>(), userDetails);
    }

    private String generateToken(
            Map<String, Object> claims,
            UserDetails userDetails
    ) {

        return buildToken(claims, userDetails, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long jwtExpiration
    ) {
        var authorities = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .claim("authorities", authorities)
                .signWith(getSignInKey())
                .compact();

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {

        String username = extractUsername(token);
        return false ;
    }



    private Key getSignInKey() {

        byte[] bytes = Decoders.BASE64.decode(secreteKey);
        return Keys.hmacShaKeyFor(bytes);
    }

}
