package com.category.server.service.impl;

import com.category.server.dto.CategoryRequestDto;
import com.category.server.dto.CategoryResponseDto;
import com.category.server.entity.Category;
import com.category.server.globalHandleException.CustomException;
import com.category.server.repository.CategoryRepository;
import com.category.server.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponseDto createNewCategory(CategoryRequestDto newCategoryData, Long id) {
        Category newCategory = modelMapper.map(newCategoryData, Category.class);
        newCategory.setSalonId(id);
        Category createdCategory = categoryRepository.save(newCategory);

        return modelMapper.map(createdCategory, CategoryResponseDto.class);
    }

    @Override
    public List<CategoryResponseDto> getAllCategoryList(Long id) {
        List<Category> categoryList = categoryRepository.findBySalonId(id);

        return categoryList.stream()
                .map(item -> modelMapper.map(item, CategoryResponseDto.class))
                .toList();
    }

    @Override
    public CategoryResponseDto readSingleCategory(Long categoryId) {
        Category singleCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new CustomException("Category not found"));

        return modelMapper.map(singleCategory, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto updateCategory(CategoryRequestDto newCategoryData, Long categoryId, Long id) {
        Category singleCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new CustomException("Category not found"));

        if (!singleCategory.getSalonId().equals(id)) {
            throw new CustomException("You are not allow to update this task");
        }

        if (newCategoryData.getCategoryImage() != null) {
            singleCategory.setCategoryImage(newCategoryData.getCategoryImage());
        }

        if (newCategoryData.getCategoryName() != null) {
            singleCategory.setCategoryName(newCategoryData.getCategoryName());
        }
        Category updatedCategory = categoryRepository.save(singleCategory);

        return modelMapper.map(updatedCategory, CategoryResponseDto.class);
    }

    @Override
    public String deleteCategory(Long categoryId, Long id) {
        Category singleCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new CustomException("Category not found"));
        if (!singleCategory.getSalonId().equals(id)) {
            throw new CustomException("You are not allow to update this task");
        }
        categoryRepository.deleteById(categoryId);
        return "Delete category successfully.";
    }
}
