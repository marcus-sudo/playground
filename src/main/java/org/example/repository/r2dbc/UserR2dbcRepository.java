package org.example.repository.r2dbc;

import org.example.entity.UserR2dbc;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("r2dbc")
public interface UserR2dbcRepository extends R2dbcRepository<UserR2dbc, String> {
}