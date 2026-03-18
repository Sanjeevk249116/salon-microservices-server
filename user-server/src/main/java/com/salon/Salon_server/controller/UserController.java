package com.salon.Salon_server.controller;

import com.salon.Salon_server.dto.UserDto;
import com.salon.Salon_server.entity.User;
import com.salon.Salon_server.payloadResponse.ApiResponse;
import com.salon.Salon_server.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUserProfile(@RequestBody @Valid UserDto newUser) {
        User user = userService.createUserProfile(newUser);
        return ResponseEntity.status(201).body(user);
    }

    @GetMapping("/read/user-profile/{userId}")
    public ResponseEntity<User> getUserProfileById(@PathVariable Long userId) {
        User userProfile = userService.getUserProfileById(userId);
        return ResponseEntity.status(200).body(userProfile);
    }

    @GetMapping("/read/user-list")
    public ResponseEntity<List<User>> getUserList() {
        List<User> userList = userService.getAllUserList();
        return ResponseEntity.status(200).body(userList);
    }

    @GetMapping("/read/user-profile")
    public ResponseEntity<User> getAllUserProfile() {
        User user = userService.getUserProfile();
        return ResponseEntity.status(200).body(user);
    }

    @PutMapping("/update/user-profile/{userId}")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long userId, @RequestBody @Valid UserDto updateProfileData) {
        User updatedUser = userService.updateUserProfile(userId, updateProfileData);
        return ResponseEntity.status(200).body(updatedUser);
    }

    @DeleteMapping("/delete/user-profile/{userId}")
    public ResponseEntity<ApiResponse<String>> deletUserProfile(@PathVariable Long userId) {
        ApiResponse<String> str = userService.deletUserProfile(userId);
        return ResponseEntity.status(200).body(str);

    }

}
