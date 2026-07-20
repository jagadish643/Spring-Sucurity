package com.example.SpringDataJpaDemo.service;

import com.example.SpringDataJpaDemo.dto.CreateOrderDto;
import com.example.SpringDataJpaDemo.dto.CreateUserDto;
import com.example.SpringDataJpaDemo.dto.OrderDto;
import com.example.SpringDataJpaDemo.dto.UserDto;
import com.example.SpringDataJpaDemo.entities.User;
import com.example.SpringDataJpaDemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public UserDto saveUser(CreateUserDto createUserDto) {
        User user=new User();
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());
        User savedUser=userRepository.save(user);
        return new UserDto(savedUser.getId(),savedUser.getName(),savedUser.getEmail());

    }

    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail()))
                .toList();
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deletUser(Long id) {
        userRepository.deleteById(id);
    }
    @Transactional
    public UserDto updateUser(Long id, CreateUserDto updateUserDto) {
        User user=userRepository.findById(id).orElseThrow();
        user.setName(updateUserDto.getName());
        user.setEmail(updateUserDto.getEmail());

        return new UserDto(user.getId(),user.getName(),user.getEmail());
    }
@Transactional
    public UserDto patchUser(Long id, CreateUserDto patchUserDto) {
        User user=userRepository.findById(id).orElseThrow();
        if (patchUserDto.getName()!=null){
            user.setName(patchUserDto.getName());
        }
        if(patchUserDto.getEmail()!=null){
            user.setEmail(patchUserDto.getEmail());
        }
    return new UserDto(user.getId(),user.getName(),user.getEmail());
    }


}
