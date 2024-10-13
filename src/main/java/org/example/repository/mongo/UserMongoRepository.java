package org.example.repository.mongo;

import org.example.entity.UserMongo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

@Repository
@Profile("mongo")
public interface UserMongoRepository extends ReactiveMongoRepository<UserMongo, String> {
}