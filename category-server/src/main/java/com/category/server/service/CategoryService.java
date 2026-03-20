package com.category.server.service;

import com.category.server.dto.CategoryRequestDto;
import com.category.server.dto.CategoryResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    CategoryResponseDto createNewCategory(CategoryRequestDto newCategoryData, Long id);
}
