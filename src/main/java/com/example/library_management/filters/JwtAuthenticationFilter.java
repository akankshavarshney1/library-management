package com.example.library_management.filters;

import com.example.library_management.config.JwtTokenUtil;
import com.example.library_management.entities.SessionToken;
import com.example.library_management.exception.BusinessException;
import com.example.library_management.helper.CustomUserDetails;
import com.example.library_management.repositories.SessionTokenRepository;
import com.example.library_management.utils.Constants.CustomStatusCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SessionTokenRepository sessionTokenRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        try {
            String jwt = extractToken(authorizationHeader);
            if (jwt != null) {
                String username = jwtTokenUtil.getUserNameFromJwtToken(jwt);
                validateUserAuthentication(request, jwt, username);
            }

            chain.doFilter(request, response);

        } catch (BusinessException ex) {
            handleCustomException(response, ex);
        } catch (Exception ex) {
            handleGlobalException(response, ex);
        }
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private void validateUserAuthentication(HttpServletRequest request, String jwt, String username) throws BusinessException {
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

            if (userDetails == null) {
                logger.error("User not found with username: {}", username);
                throw new BusinessException(CustomStatusCode.INVALID_USER_ID, "User not found");
            }

            Optional<SessionToken> sessionTokenOpt = sessionTokenRepository.findByToken(jwt);

            if (sessionTokenOpt.isEmpty()) {
                logger.warn("Invalid token provided for user: {}", username);
                throw new BusinessException(CustomStatusCode.INVALID_TOKEN, "Invalid token");
            }

            SessionToken sessionToken = sessionTokenOpt.get();

            if (jwtTokenUtil.validateToken(jwt, userDetails) && sessionToken.getExpireTime() > System.currentTimeMillis()) {
                setAuthenticationContext(request, userDetails);
            } else {
                logger.warn("Expired or invalid token for user: {}", username);
                throw new BusinessException(CustomStatusCode.TOKEN_EXPIRED, "Token is expired or invalid");
            }
        }
    }

    private void setAuthenticationContext(HttpServletRequest request, CustomUserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private void handleCustomException(HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String jsonResponse;
        if (ex instanceof BusinessException jwtEx) {
            jsonResponse = String.format("{\"errorCode\": \"%s\", \"message\": \"%s\"}",
                    jwtEx.getErrorCode(), jwtEx.getMessage());
        } else {
            jsonResponse = "{\"errorCode\": \"UNKNOWN_ERROR\", \"message\": \"An unknown error occurred.\"}";
        }

        response.getWriter().write(jsonResponse);
        logger.error("Custom exception occurred: {}", ex.getMessage());
    }

    private void handleGlobalException(HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"errorCode\": \"%s\", \"message\": \"%s\"}",
                CustomStatusCode.INTERNAL_APPLICATION_ERROR, "An unexpected error occurred."));
        logger.error("Unexpected error occurred", ex);
    }
}
