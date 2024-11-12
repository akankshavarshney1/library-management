package com.example.library_management.services.implementation;

import com.example.library_management.dto.Object.UserObject;
import com.example.library_management.entities.User;
import com.example.library_management.exception.BusinessException;
import com.example.library_management.helper.CustomUserDetails;
import com.example.library_management.repositories.UserRepository;
import com.example.library_management.services.UserService;
import com.example.library_management.utils.Constants.CustomStatusCode;
import com.example.library_management.utils.enums.RoleType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserObject> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserObject.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserObject> getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserObject.class));
    }

    @Override
    public UserObject updateUser(Long id, UserObject userUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userUpdateDTO.getUsername());
        user.setEmail(userUpdateDTO.getEmail());

        user.setEnabled(userUpdateDTO.isEnabled());

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserObject.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }

    @Override
    public UserObject updateUserRole(Long id, UserObject userRoleUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String role = userRoleUpdateDTO.getRole();
        RoleType roleType = RoleType.getRoleTypeByString(role);
        if(roleType == null){
            throw new RuntimeException("Invalid role");
        }

        user.setRole(roleType);

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserObject.class);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new BusinessException(CustomStatusCode.USER_NOT_FOUND, "User not found with username: " + username));

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), authorities);
    }
}
