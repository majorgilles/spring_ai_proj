package com.example.aimobilebackend.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.example.aimobilebackend.interfaces.rest.dto.UserRequest;
import com.example.aimobilebackend.interfaces.rest.dto.UserResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserIntegrationTest {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void userLifecycle_shouldWorkEndToEnd() {
    String baseUrl = "http://localhost:" + port + "/api/users";

    // 1. Create a user
    UserRequest createRequest = new UserRequest();
    createRequest.setUsername("integrationuser");
    createRequest.setEmail("integration@example.com");

    ResponseEntity<UserResponse> createResponse =
        restTemplate.postForEntity(baseUrl, createRequest, UserResponse.class);

    assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
    assertNotNull(createResponse.getBody());
    assertNotNull(createResponse.getBody().getId());
    assertEquals("integrationuser", createResponse.getBody().getUsername());

    // Store the created user ID
    String userId = createResponse.getBody().getId().toString();

    // 2. Get user by ID
    ResponseEntity<UserResponse> getResponse =
        restTemplate.getForEntity(baseUrl + "/" + userId, UserResponse.class);

    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    assertNotNull(getResponse.getBody());
    assertEquals(userId, getResponse.getBody().getId().toString());

    // 3. Update username - Print response details for debugging
    ResponseEntity<String> updateResponseRaw =
        restTemplate.exchange(
            baseUrl + "/" + userId + "/username?username=updateduser",
            HttpMethod.PUT,
            HttpEntity.EMPTY,
            String.class);

    System.out.println("Update response status: " + updateResponseRaw.getStatusCode());
    System.out.println("Update response body: " + updateResponseRaw.getBody());

    ResponseEntity<UserResponse> updateResponse =
        restTemplate.exchange(
            baseUrl + "/" + userId + "/username?username=updateduser",
            HttpMethod.PUT,
            HttpEntity.EMPTY,
            UserResponse.class);

    assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
    assertNotNull(updateResponse.getBody());
    assertEquals("updateduser", updateResponse.getBody().getUsername());

    // 4. Get all users
    ResponseEntity<UserResponse[]> getAllResponse =
        restTemplate.getForEntity(baseUrl, UserResponse[].class);

    assertEquals(HttpStatus.OK, getAllResponse.getStatusCode());
    assertTrue(getAllResponse.getBody().length > 0);

    // 5. Delete user
    restTemplate.delete(baseUrl + "/" + userId);

    // 6. Verify user is deleted
    ResponseEntity<UserResponse> getDeletedResponse =
        restTemplate.getForEntity(baseUrl + "/" + userId, UserResponse.class);

    assertEquals(HttpStatus.NOT_FOUND, getDeletedResponse.getStatusCode());
  }
}
