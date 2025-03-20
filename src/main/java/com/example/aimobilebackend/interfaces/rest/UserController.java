package com.example.aimobilebackend.interfaces.rest;

import com.example.aimobilebackend.application.dto.UserDto;
import com.example.aimobilebackend.application.port.in.UserUseCase;
import com.example.aimobilebackend.interfaces.rest.dto.UserRequest;
import com.example.aimobilebackend.interfaces.rest.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserUseCase userUseCase;
    
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserDto userDto = userUseCase.createUser(request.getUsername(), request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(userDto));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        return userUseCase.getUserById(id)
                .map(userDto -> ResponseEntity.ok(mapToResponse(userDto)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userUseCase.getAllUsers().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
    
    @PutMapping("/{id}/username")
    public ResponseEntity<UserResponse> updateUsername(@PathVariable UUID id, 
                                                       @RequestParam String username) {
        try {
            return userUseCase.updateUsername(id, username)
                    .map(userDto -> ResponseEntity.ok(mapToResponse(userDto)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error updating username: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    @PutMapping("/{id}/email")
    public ResponseEntity<UserResponse> updateEmail(@PathVariable UUID id, 
                                                    @RequestParam String email) {
        return userUseCase.updateEmail(id, email)
                .map(userDto -> ResponseEntity.ok(mapToResponse(userDto)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    private UserResponse mapToResponse(UserDto userDto) {
        return UserResponse.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .createdAt(userDto.getCreatedAt())
                .updatedAt(userDto.getUpdatedAt())
                .build();
    }
}