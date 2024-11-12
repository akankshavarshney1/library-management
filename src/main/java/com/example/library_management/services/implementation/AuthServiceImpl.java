package com.example.library_management.services.implementation;

import com.example.library_management.dto.Object.UserObject;
import com.example.library_management.entities.SessionToken;
import com.example.library_management.entities.User;
import com.example.library_management.exception.BusinessException;
import com.example.library_management.repositories.SessionTokenRepository;
import com.example.library_management.repositories.UserRepository;
import com.example.library_management.services.AuthService;
import com.example.library_management.config.JwtTokenUtil;
import com.example.library_management.helper.CustomUserDetails;
import com.example.library_management.utils.Constants.CustomStatusCode;
import com.example.library_management.utils.enums.RoleType;
import io.jsonwebtoken.Claims;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionTokenRepository sessionTokenRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CompromisedPasswordChecker compromisedPasswordChecker;

    @Override
    public UserObject register(UserObject registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new RuntimeException("User already exists");
        }
        String role = registerDto.getRole();
        RoleType roleType = RoleType.getRoleTypeByString(role);
        if(roleType == null){
            throw new RuntimeException("Invalid role");
        }

        CompromisedPasswordDecision check = compromisedPasswordChecker.check(registerDto.getPassword());
        if (check.isCompromised()) {
            throw new BusinessException(CustomStatusCode.COMPROMISED_PASSWORD, "Password is compromised");
        }
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(roleType);
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserObject.class);
    }


    public Map<String, String> login(UserObject loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        CustomUserDetails userDetails = new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getRole().name())
        );

        String token = jwtTokenUtil.generateToken(userDetails); // Generate JWT token
        SessionToken sessionToken = new SessionToken();
        sessionToken.setUserId(user.getId());
        sessionToken.setRoleType(user.getRole().name());
        sessionToken.setToken(token);
        sessionToken.setStatus("ACTIVE");
        sessionToken.setExpireTime(jwtTokenUtil.getExpirationDateFromToken(token).getTime()); // example expiration

        sessionTokenRepository.save(sessionToken);
        Map<String, String> response = new HashMap<>();
        response.put("jwtToken", token);

        return response;
    }

    @Override
    public Map<String, String> refreshToken(String refreshToken) {
        String jwt = JwtTokenUtil.getJwtFromHttpServletRequest(refreshToken);
        User user = jwtTokenUtil.getUserFromJwt(jwt);
        Optional<SessionToken> sessionOpt = sessionTokenRepository.findByToken(jwt);
        if(sessionOpt.isPresent()){
            SessionToken session = sessionOpt.get();
            session.setStatus("INACTIVE");
            String username = jwtTokenUtil.getUserNameFromJwtToken(jwt);
            final CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
            String newToken = jwtTokenUtil.generateToken(userDetails);
            if(newToken != null && !newToken.isEmpty()){
                sessionTokenRepository.save(session);

                SessionToken sessionToken = new SessionToken();
                sessionToken.setUserId(user.getId());
                sessionToken.setRoleType(user.getRole().name());
                sessionToken.setToken(newToken);
                sessionToken.setStatus("ACTIVE");
                sessionToken.setExpireTime(jwtTokenUtil.getExpirationDateFromToken(newToken).getTime());
                sessionTokenRepository.save(sessionToken);

                Map<String, String> map = new HashMap<>();
                map.put("refreshedToken", newToken);
                return map;
            }

        }else {
            throw new BusinessException("Invalid token");
        }
        return null;
    }

    @Override
    public void logout(String token) {
        String jwt = JwtTokenUtil.getJwtFromHttpServletRequest(token);
        Optional<SessionToken> sessionOpt = sessionTokenRepository.findByToken(jwt);
        if(sessionOpt.isPresent()){
            SessionToken sessionToken = sessionOpt.get();
            sessionToken.setStatus("INACTIVE");
            sessionTokenRepository.save(sessionToken);

        }
    }

    @Override
    public UserObject getCurrentUser(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String username = jwtTokenUtil.getUserNameFromJwtToken(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return modelMapper.map(user, UserObject.class);
    }
}
