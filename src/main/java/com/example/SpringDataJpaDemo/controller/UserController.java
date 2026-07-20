package com.example.SpringDataJpaDemo.controller;

import com.example.SpringDataJpaDemo.dto.CreateUserDto;
import com.example.SpringDataJpaDemo.dto.UserDto;
import com.example.SpringDataJpaDemo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;

//    @PostMapping
//    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto createUserDto){
//        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(createUserDto));
//    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

//    @GetMapping
//    public ResponseEntity<List<UserDto>> getUsersPaginated(@RequestParam int page,@RequestParam int PageSize,
//                                                           @RequestParam String direction,
//                                                           @RequestParam String sortBy
//                                                           ){
//        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
//    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getuser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deletUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> patchUser(@PathVariable Long id,@RequestBody CreateUserDto patchUserDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.patchUser(id,patchUserDto));

    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,@RequestBody CreateUserDto updateUserDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id,updateUserDto));

    }

}
