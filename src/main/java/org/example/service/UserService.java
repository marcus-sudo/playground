package org.example.service;

import org.example.dto.UserDTO;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface UserService {
    Mono<UserDTO> createUser(UserDTO userDTO);
    Mono<UserDTO> getUserById(String id);
    Flux<UserDTO> getAllUsers();
    Mono<Void> deleteUser(String id);
}
