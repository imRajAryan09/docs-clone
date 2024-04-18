package com.google.docs.entity;

import com.google.docs.enums.AuthProvider;
import com.google.docs.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "users")
@RequiredArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "email_verified")
    private Boolean emailVerified = false;

    @Column
    private String providerId;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "source")
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date updatedDate;
}
