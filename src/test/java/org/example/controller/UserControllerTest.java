package org.example.controller;

import org.example.dto.UserDTO;
import org.example.exception.UserNotFoundException;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(UserController.class)
class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testGetUserById() {
        UserDTO mockUser = new UserDTO("1", "myUserName", "myFirstName", "myLastName", "myEmail@playground.com", "1234567890");

        when(userService.getUserById("1")).thenReturn(Mono.just(mockUser));

        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.firstName").isEqualTo("myFirstName")
                .jsonPath("$.email").isEqualTo("myEmail@playground.com");
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        when(userService.getUserById("1")).thenReturn(Mono.empty());

        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateUser() {
        UserDTO newUser = new UserDTO(null, "myUserName", "myFirstName", "myLastName", "myEmail@playground.com", "1234567890");
        UserDTO createdUser = new UserDTO("1", "myUserName", "myFirstName", "myLastName", "myEmail@playground.com", "1234567890");

        when(userService.createUser(newUser)).thenReturn(Mono.just(createdUser));

        webTestClient.post()
                .uri("/users")
                .contentType(APPLICATION_JSON)
                .bodyValue(newUser)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.firstName").isEqualTo("myFirstName");
    }

    @Test
    void testCreateUser_Duplicate() throws Exception {
        UserDTO newUser = new UserDTO(null, "myUserName", "myFirstName", "myLastName", "myEmail@playground.com", "1234567890");

        when(userService.createUser(any(UserDTO.class)))
                .thenReturn(Mono.error(new DuplicateKeyException("Duplicate user")));

        webTestClient.post()
                .uri("/users")
                .contentType(APPLICATION_JSON)
                .bodyValue(newUser)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testDeleteUser() {
        UserDTO mockUser = new UserDTO("1", "myUserName", "myFirstName", "myLastName", "myEmail@playground.com", "1234567890");

        when(userService.getUserById("1")).thenReturn(Mono.just(mockUser));
        when(userService.deleteUser("1")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/users/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testUpdateUser() {
        UserDTO existingUser = new UserDTO("1", "myUserName", "myFirstName", "myLastName", "myEmail@playground.com", "1234567890");
        UserDTO updatedUser = new UserDTO("1", "myUserName", "myFirstName", "myLastName", "myEmail@playground.com", "1234567890");

        when(userService.getUserById("1")).thenReturn(Mono.just(existingUser));
        when(userService.createUser(updatedUser)).thenReturn(Mono.just(updatedUser));

        webTestClient.put()
                .uri("/users/1")
                .contentType(APPLICATION_JSON)
                .bodyValue(updatedUser)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.lastName").isEqualTo("myLastName")
                .jsonPath("$.phoneNumber").isEqualTo("1234567890")
                .jsonPath("$.email").isEqualTo("myEmail@playground.com");
    }

    @Test
    void testDeleteUser_NotFound() throws Exception {
        when(userService.getUserById("1")).thenReturn(Mono.error(new UserNotFoundException("User not found")));

        webTestClient.delete()
                .uri("/users/1")
                .exchange()
                .expectStatus().isNotFound();
    }

}