package com.Book_social_Network.security;

import io.jsonwebtoken.Claims;
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
import java.util.function.Function;

@Service // Indicates that this class is a service component in the Spring context.
public class JwtService {

    @Value("${application.security.jwt.expiration}") // Injects the JWT expiration time from application properties.
    private long jwtExpiration;

    @Value("${application.security.jwt.secrete-key}") // Injects the secret key for signing JWTs from application properties.
    private String secreteKey;

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token the JWT token.
     * @return the username extracted from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Extracts the subject (username) from the claims.
    }

    /**
     * Generates a new JWT token for the given user details.
     *
     * @param userDetails the user details for which to generate the token.
     * @return the generated JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<String, Object>(), userDetails); // Generates a token with no additional claims.
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, jwtExpiration); // Builds and returns the token with the specified claims and expiration.
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long jwtExpiration) {
        // Maps user authorities to a list for inclusion in the token.
        var authorities = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts
                .builder() // Begins the building process for a JWT.
                .setClaims(extraClaims) // Sets any additional claims.
                .setSubject(userDetails.getUsername()) // Sets the subject of the token (username).
                .setIssuedAt(new Date(System.currentTimeMillis())) // Sets the issued date.
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Sets the expiration date.
                .claim("authorities", authorities) // Adds user authorities to the claims.
                .signWith(getSignInKey()) // Signs the token using the signing key.
                .compact(); // Builds the final JWT string.
    }

    /**
     * Extracts a specific claim from the token using the provided resolver function.
     *
     * @param token the JWT token.
     * @param claimsResolver a function to extract the claim.
     * @param <T> the type of the claim.
     * @return the extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token); // Extracts all claims from the token.
        return claimsResolver.apply(claims); // Applies the provided function to the claims to extract the desired value.
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder() // Begins the parsing process for a JWT.
                .setSigningKey(getSignInKey()) // Sets the signing key for validation.
                .build()
                .parseClaimsJws(token) // Parses the token and retrieves the claims.
                .getBody(); // Gets the claims body.
    }

    /**
     * Checks if the provided token is valid for the given user details.
     *
     * @param token the JWT token.
     * @param userDetails the user details to validate against.
     * @return true if the token is valid; false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token); // Extracts the username from the token.
        // Validates the token by checking the username and expiration.
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Checks if the token's expiration date is before the current date.
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Extracts the expiration date from the claims.
    }

    private Key getSignInKey() {
        // Decodes the base64 encoded secret key and returns it as a Key object.
        byte[] bytes = Decoders.BASE64.decode(secreteKey);
        return Keys.hmacShaKeyFor(bytes); // Creates an HMAC signing key for signing the JWT.
    }
}
