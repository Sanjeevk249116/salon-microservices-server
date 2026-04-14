package com.service_offering.server.service.client;

import com.service_offering.server.config.FeignConfig;
import com.service_offering.server.dto.CategoryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "CATEGORY-SERVER", path = "/api/category", configuration = FeignConfig.class)
public interface CategoryClient {

    @GetMapping("/read-single-category/{categoryId}")
    ResponseEntity<CategoryResponseDto> readSingleCategory(@PathVariable Long categoryId,@RequestHeader("Authorization") String authHeader);
}
