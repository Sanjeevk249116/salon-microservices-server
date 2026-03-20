package com.category.server.service.impl;

import com.category.server.dto.CategoryRequestDto;
import com.category.server.dto.CategoryResponseDto;
import com.category.server.entity.Category;
import com.category.server.repository.CategoryRepository;
import com.category.server.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponseDto createNewCategory(CategoryRequestDto newCategoryData, Long id) {
        Category newCategory=modelMapper.map(newCategoryData,Category.class);
        newCategory.setSalonId(id);
        Category createdCategory=categoryRepository.save(newCategory);

        return modelMapper.map(createdCategory,CategoryResponseDto.class);
    }
}
