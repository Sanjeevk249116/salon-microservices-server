package com.service_offering.server.service.client;

import com.service_offering.server.dto.CategoryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "CATEGORY-SERVER",path = "/api/category")
public interface CategoryClient {

    @GetMapping("/read-single-category/{categoryId}")
    ResponseEntity<CategoryResponseDto> readSingleCategory(@PathVariable Long categoryId);
}
