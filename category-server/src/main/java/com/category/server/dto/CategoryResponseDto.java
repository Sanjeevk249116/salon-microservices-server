package com.category.server.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryResponseDto {
    private Long id;
    private String categoryName;
    private String categoryImage;
    private LocalDateTime updateAt;
    private LocalDateTime createAt;
}
