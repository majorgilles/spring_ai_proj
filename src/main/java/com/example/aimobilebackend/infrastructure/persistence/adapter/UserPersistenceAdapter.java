package com.example.aimobilebackend.infrastructure.persistence.adapter;

import com.example.aimobilebackend.domain.model.aggregate.User;
import com.example.aimobilebackend.domain.model.valueobject.UserId;
import com.example.aimobilebackend.domain.repository.UserRepository;
import com.example.aimobilebackend.infrastructure.persistence.entity.UserEntity;
import com.example.aimobilebackend.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepository {
    
    private final UserJpaRepository userJpaRepository;
    
    @Override
    public User save(User user) {
        UserEntity savedEntity = userJpaRepository.save(mapToEntity(user));
        return mapToDomain(savedEntity);
    }
    
    @Override
    public Optional<User> findById(UserId id) {
        return userJpaRepository.findById(id.getValue())
                .map(this::mapToDomain);
    }
    
    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public void delete(UserId id) {
        userJpaRepository.deleteById(id.getValue());
    }
    
    private UserEntity mapToEntity(User user) {
        // Find existing entity first to preserve version information
        UserEntity existingEntity = userJpaRepository.findById(user.getId().getValue()).orElse(null);
        
        UserEntity.UserEntityBuilder builder = UserEntity.builder()
                .id(user.getId().getValue())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt());
        
        // Preserve version if entity already exists
        if (existingEntity != null) {
            builder.version(existingEntity.getVersion());
        } else {
            builder.version(0);
        }
        
        return builder.build();
    }
    
    private User mapToDomain(UserEntity entity) {
        return User.createFromEntity(
                UserId.of(entity.getId()),
                entity.getUsername(),
                entity.getEmail(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}