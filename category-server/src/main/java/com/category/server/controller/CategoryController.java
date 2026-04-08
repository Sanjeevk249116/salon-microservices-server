package com.category.server.controller;

import com.category.server.dto.CategoryRequestDto;
import com.category.server.dto.CategoryResponseDto;
import com.category.server.dto.SalonDto;
import com.category.server.dto.SalonResponseDto;
import com.category.server.globalHandleException.CustomException;
import com.category.server.service.CategoryService;
import com.category.server.service.client.GetSalonDetailClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final GetSalonDetailClient getSalonDetailClient;

    @PostMapping("/owner/create-category/{salonId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<CategoryResponseDto> createNewCategory(@RequestBody @Valid CategoryRequestDto newCategoryData, @PathVariable Long salonId, @AuthenticationPrincipal Jwt jwt) {


        List<SalonResponseDto> salonResponseDto = getSalonDetailClient.getMySalonDetails("Bearer " + jwt.getTokenValue()).getBody();

        assert salonResponseDto != null;
        if (salonResponseDto.isEmpty()) {
            throw new CustomException("Salon not found");
        }

        boolean exists = salonResponseDto.stream()
                .anyMatch(salon -> salon.getId().equals(salonId));

        if (!exists) {
            throw new CustomException("You are not allowed to create a new category for this salon", 400);
        }

        CategoryResponseDto newCategory = categoryService.createNewCategory(newCategoryData, salonId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @GetMapping("/get-all-list/{salonId}")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategoryList(@PathVariable Long salonId) {

        List<CategoryResponseDto> categoryList = categoryService.getAllCategoryList(salonId);
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/read-single-category/{categoryId}")
    public ResponseEntity<CategoryResponseDto> readSingleCategory(@PathVariable Long categoryId) {
        CategoryResponseDto categoryResponse = categoryService.readSingleCategory(categoryId);
        return ResponseEntity.ok(categoryResponse);
    }

    @PutMapping("/owner/update/{salonId}/{categoryId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<CategoryResponseDto> updateCategory(@RequestBody @Valid CategoryRequestDto newCategoryData, @PathVariable Long salonId, @PathVariable Long categoryId, @AuthenticationPrincipal Jwt jwt) {
        List<SalonResponseDto> salonResponseDto = getSalonDetailClient.getMySalonDetails("Bearer " + jwt.getTokenValue()).getBody();

        assert salonResponseDto != null;
        if (salonResponseDto.isEmpty()) {
            throw new CustomException("Salon not found");
        }

        boolean exists = salonResponseDto.stream()
                .anyMatch(salons -> salons.getId().equals(salonId));

        if (!exists) {
            throw new CustomException("You are not allowed to create a new category for this salon", 400);
        }

        CategoryResponseDto newCategory = categoryService.updateCategory(newCategoryData, categoryId, salonId);
        return ResponseEntity.status(200).body(newCategory);
    }

    @DeleteMapping("/owner/delete/{salonId}/{categoryId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId, @PathVariable Long salonId, @AuthenticationPrincipal Jwt jwt) {
        List<SalonResponseDto> salonResponseDto = getSalonDetailClient.getMySalonDetails("Bearer " + jwt.getTokenValue()).getBody();

        assert salonResponseDto != null;
        if (salonResponseDto.isEmpty()) {
            throw new CustomException("Salon not found");
        }

        boolean exists = salonResponseDto.stream()
                .anyMatch(salons -> salons.getId().equals(salonId));

        if (!exists) {
            throw new CustomException("You are not allowed to create a new category for this salon", 400);
        }
        String str = categoryService.deleteCategory(categoryId, salonId);
        return ResponseEntity.status(200).body(str);
    }

}
