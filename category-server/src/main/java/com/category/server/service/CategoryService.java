package com.category.server.service;

import com.category.server.dto.CategoryRequestDto;
import com.category.server.dto.CategoryResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryResponseDto createNewCategory(CategoryRequestDto newCategoryData, Long id);

    List<CategoryResponseDto> getAllCategoryList(Long id);

    CategoryResponseDto readSingleCategory(Long categoryId);

    CategoryResponseDto updateCategory(@Valid CategoryRequestDto newCategoryData, Long categoryId, Long id);

    String deleteCategory(Long categoryId, Long id);
}
