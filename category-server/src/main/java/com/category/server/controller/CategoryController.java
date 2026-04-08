package com.category.server.controller;

import com.category.server.dto.CategoryRequestDto;
import com.category.server.dto.CategoryResponseDto;
import com.category.server.dto.SalonDto;
import com.category.server.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/owner/create-category")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<CategoryResponseDto> createNewCategory(@RequestBody @Valid CategoryRequestDto newCategoryData) {
        SalonDto salon = new SalonDto();
        salon.setId(1L);
        CategoryResponseDto newCategory = categoryService.createNewCategory(newCategoryData, salon.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @GetMapping("/get-all-list")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategoryList(){
        SalonDto user = new SalonDto();
        user.setId(1L);
        List<CategoryResponseDto> categoryList=categoryService.getAllCategoryList(user.getId());
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/read-single-category/{categoryId}")
    public ResponseEntity<CategoryResponseDto> readSingleCategory(@PathVariable Long categoryId){
        CategoryResponseDto categoryResponse= categoryService.readSingleCategory(categoryId);
        return ResponseEntity.ok(categoryResponse);
    }

    @PutMapping("/owner/update/{categoryId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<CategoryResponseDto> updateCategory(@RequestBody @Valid CategoryRequestDto newCategoryData,@PathVariable Long categoryId) {
        SalonDto salon = new SalonDto();
        salon.setId(1L);
        CategoryResponseDto newCategory = categoryService.updateCategory(newCategoryData,categoryId, salon.getId());
        return ResponseEntity.status(200).body(newCategory);
    }

    @DeleteMapping("/owner/delete/{categoryId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        SalonDto salon = new SalonDto();
        salon.setId(1L);
        String str = categoryService.deleteCategory(categoryId, salon.getId());
        return ResponseEntity.status(200).body(str);
    }

}
