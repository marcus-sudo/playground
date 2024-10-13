package org.example.service;

import org.example.dto.UserDTO;
import org.example.entity.UserMongo;
import org.example.mapper.UserMapper;
import org.example.repository.mongo.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Profile("mongo")
public class UserServiceMongo implements UserService {

    private final UserMongoRepository userMongoRepository;

    @Autowired
    public UserServiceMongo(UserMongoRepository userMongoRepository) {
        this.userMongoRepository = userMongoRepository;
    }

    public Mono<UserDTO> createUser(UserDTO userDTO) {
        UserMongo user = UserMapper.toMongoEntity(userDTO);
        return userMongoRepository.save(user).map(UserMapper::toDTO);
    }

    public Mono<UserDTO> getUserById(String id) {
        return userMongoRepository.findById(id).map(UserMapper::toDTO);
    }

    public Flux<UserDTO> getAllUsers() {
        return userMongoRepository.findAll().map(UserMapper::toDTO);
    }

    public Mono<Void> deleteUser(String id) {
        return userMongoRepository.deleteById(id);
    }
}