package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "users")
public class UserMongo {
    @Id
    private String id;

    private String userName;

    private String firstName;

    private String lastName;

    @Indexed(unique = true)
    private String email;

    private String phoneNumber;
}
