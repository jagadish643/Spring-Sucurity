package com.example.SpringDataJpaDemo.service;


import com.example.SpringDataJpaDemo.dto.*;
import com.example.SpringDataJpaDemo.entities.Role;
import com.example.SpringDataJpaDemo.entities.User;
import com.example.SpringDataJpaDemo.repository.UserRepository;
import com.example.SpringDataJpaDemo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    public RegisterUserDto registerUser(CreateUserDto createUserDto){
        User user=new User();
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setRole(Role.USER);
        User savedUser=userRepository.save(user);
        return new RegisterUserDto(savedUser.getName(), savedUser.getId());


    }

    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );
        String jwtToken=jwtService.generateJwtToken((UserDetails) Objects.requireNonNull(authentication.getPrincipal()));
        return new LoginResponseDto(jwtToken);
    }
}
