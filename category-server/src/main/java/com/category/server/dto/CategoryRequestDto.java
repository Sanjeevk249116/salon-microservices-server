package com.category.server.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDto {

    @NotBlank(message = "Category is required")
    private String categoryName;
    @NotBlank(message = "Category image is required")
    private String categoryImage;
}
