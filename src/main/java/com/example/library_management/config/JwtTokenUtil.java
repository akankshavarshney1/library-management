package com.example.library_management.config;

import com.example.library_management.entities.User;
import com.example.library_management.exception.BusinessException;
import com.example.library_management.helper.CustomUserDetails;
import com.example.library_management.repositories.UserRepository;
import com.example.library_management.utils.Constants.CustomStatusCode;
import com.example.library_management.utils.enums.RoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String SECRET_KEY = "dummy-jwt-secret-key-for-testing-only123456789"; // Ensure this is at least 256 bits
    private static final Long EXPIRE_TIME = 3600000L; // Token expiration time (in milliseconds)

    @Autowired
    private UserRepository userRepository;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRE_TIME);

        logger.info("Generating token for userId: {}, will expire at: {}", userDetails.getUsername(), expirationDate);

        return Jwts.builder()
                .setIssuer("Library Management")
                .setSubject(userDetails.getUsername())
                .claim("username", userDetails.getUsername())
                .claim("userId", userDetails.getUserId())
                .claim("authorities", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromJwtToken(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getUserNameFromJwtToken(String token) {
        try {
            if (isTokenExpired(token)) {
                logger.warn("Token is expired: {}", token);
                return null;
            }
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            logger.error("Failed to parse JWT token: {}", e.getMessage());
            return null;
        }
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("Failed to extract claims from JWT token: {}", e.getMessage());
            return null;
        }
    }

    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims != null && claims.getExpiration().before(new Date());
        } catch (Exception e) {
            logger.error("Failed to check if JWT token is expired: {}", e.getMessage());
            return true;
        }
    }

    public Date getExpirationDateFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims != null ? claims.getExpiration() : null;
    }

    public boolean hasRole(String token, RoleType roleType) {
        Claims claims = extractAllClaims(token);
        String authorities = claims.get("authorities", String.class);
        return authorities != null && authorities.contains(roleType.name());
    }

    public boolean isAdmin(String token) {
        return hasRole(token, RoleType.ADMIN);
    }

    public boolean isLibrarian(String token) {
        return hasRole(token, RoleType.LIBRARIAN);
    }

    public boolean isUser(String token) {
        return hasRole(token, RoleType.USER);
    }

    public static String getJwtFromHttpServletRequest(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public User getUserFromJwt(String jwt) {
        Claims claims = this.extractAllClaims(jwt);
        Long userId = claims.get("userId", Long.class);
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(CustomStatusCode.USER_NOT_FOUND, "User not found for ID: " + userId));
    }
}
