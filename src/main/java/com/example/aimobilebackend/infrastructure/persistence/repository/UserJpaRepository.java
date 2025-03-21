package com.example.aimobilebackend.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.aimobilebackend.infrastructure.persistence.entity.UserEntity;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {}
