package com.service_offering.server.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ServiceIdsDto {
    private Set<Long> servicesIds;
}
