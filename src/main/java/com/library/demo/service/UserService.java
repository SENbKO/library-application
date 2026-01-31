package com.library.demo.service;

import com.library.demo.auth.JwtUtil;
import com.library.demo.dto.UserDto;
import com.library.demo.exception.InvalidCredentialsException;
import com.library.demo.dto.LoginDto;
import com.library.demo.model.user_model.Role;
import com.library.demo.model.user_model.Status;
import com.library.demo.model.user_model.User;
import com.library.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String saveUser(UserDto userDto){

        String password = passwordEncoder.encode(userDto.getPassword());
        Role role = Role.valueOf(userDto.getRole().toUpperCase());
        User user = User.builder()
                .email(userDto.getEmail())
                .password(password)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .role(role)
                .build();
        userRepository.save(user);
        return jwtUtil.generateToken(user);
    }

    public String findUser(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            System.out.println("invalid password");
            throw new InvalidCredentialsException();

        }

        return jwtUtil.generateToken(user);
    }
}
