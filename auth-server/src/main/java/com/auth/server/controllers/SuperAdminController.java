package com.auth.server.controllers;


import com.auth.server.dto.request.UpdateRoleRequest;
import com.auth.server.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/admin")
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    @PatchMapping("/update/profile-role/{profileId}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<String> updateProfileRole(@PathVariable Long profileId, @RequestBody UpdateRoleRequest updateRoleRequest) {
        String userResponse = superAdminService.updateProfileRole(profileId, updateRoleRequest.getRole());
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
}
