package com.category.server.controller;

import com.category.server.dto.CategoryRequestDto;
import com.category.server.dto.CategoryResponseDto;
import com.category.server.dto.UserDto;
import com.category.server.entity.Category;
import com.category.server.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create-category")
    public ResponseEntity<CategoryResponseDto> createNewCategory(@RequestBody @Valid CategoryRequestDto newCategoryData) {
        UserDto user = new UserDto();
        user.setId(1L);
        CategoryResponseDto newCategory = categoryService.createNewCategory(newCategoryData, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }


}
