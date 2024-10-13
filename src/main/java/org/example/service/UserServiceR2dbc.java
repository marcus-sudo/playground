package org.example.service;

import org.example.dto.UserDTO;
import org.example.entity.UserR2dbc;
import org.example.mapper.UserMapper;
import org.example.repository.r2dbc.UserR2dbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Service
@Profile("r2dbc")
public class UserServiceR2dbc implements UserService {
    private final UserR2dbcRepository userR2dbcRepository;

    @Autowired
    public UserServiceR2dbc(UserR2dbcRepository userR2dbcRepository) {
        this.userR2dbcRepository = userR2dbcRepository;
    }

    public Mono<UserDTO> createUser(UserDTO userDTO) {
        UserR2dbc user = UserMapper.toR2dbcEntity(userDTO);
        return userR2dbcRepository.save(user).map(UserMapper::toDTO);
    }
    public Mono<UserDTO> getUserById(String id) {
        return userR2dbcRepository.findById(id).map(UserMapper::toDTO);
    }

    public Flux<UserDTO> getAllUsers() {
        return userR2dbcRepository.findAll().map(UserMapper::toDTO);
    }

    public Mono<Void> deleteUser(String id) {
        return userR2dbcRepository.deleteById(id);
    }
}