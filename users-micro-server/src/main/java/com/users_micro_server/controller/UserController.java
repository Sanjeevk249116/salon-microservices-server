package com.users_micro_server.controller;


import com.users_micro_server.dto.UserDto;
import com.users_micro_server.dto.UserResponseDto;
import com.users_micro_server.entity.User;
import com.users_micro_server.enums.RoleEnum;
import com.users_micro_server.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUserProfile(@RequestBody @Valid UserDto newUser, @AuthenticationPrincipal Jwt jwt) {
        List<String> rolesList = jwt.getClaim("roles");

        Set<String> roles = new HashSet<>(rolesList);

        Set<RoleEnum> roleEnums = roles.stream()
                .map(RoleEnum::valueOf)
                .collect(Collectors.toSet());
        User user = userService.createUserProfile(newUser, roleEnums);
        return ResponseEntity.status(201).body(user);
    }

    @GetMapping("/read/user-profile/{userId}")
    public ResponseEntity<User> getUserProfileById(@PathVariable Long userId) {
        User userProfile = userService.getUserProfileById(userId);
        return ResponseEntity.status(200).body(userProfile);
    }

    @GetMapping("/admin/read/user-list")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
    public ResponseEntity<List<User>> getUserList() {
        List<User> userList = userService.getAllUserList();
        return ResponseEntity.status(200).body(userList);
    }

    @GetMapping("/read/user-profile")
    public ResponseEntity<UserResponseDto> getUserProfile(@AuthenticationPrincipal Jwt jwt) {

        UserResponseDto user = userService.getUserProfile(jwt.getSubject());
        return ResponseEntity.status(200).body(user);
    }

    @PutMapping("/update/user-profile/{userId}")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long userId, @RequestBody @Valid UserDto updateProfileData) {
        User updatedUser = userService.updateUserProfile(userId, updateProfileData);
        return ResponseEntity.status(200).body(updatedUser);
    }

    @DeleteMapping("/admin/delete/user-profile/{userId}")
    public ResponseEntity<String> deletUserProfile(@PathVariable Long userId) {
        String str = userService.deletUserProfile(userId);
        return ResponseEntity.status(200).body(str);

    }

}
