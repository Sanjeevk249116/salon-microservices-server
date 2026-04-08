package com.service_offering.server.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class ServiceOffering {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String serviceOfferingName;
    private String description;
    private int price;
    private int duration;

    @Column(nullable = false)
    private Long salonId;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private Long ownerId;

    private String serviceOfferImage;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
