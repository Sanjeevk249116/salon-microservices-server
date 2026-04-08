package com.auth.server.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_auths")
@Data
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(unique = true, nullable = false, name = "phone_number")
    private String phoneNumber;

    private boolean enabled = true;

    @Column(nullable = false, name = "account_non_locked")
    private boolean accountNonLocked = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_auth_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    @Column(nullable = false)
    private Set<Role> role = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;
}
