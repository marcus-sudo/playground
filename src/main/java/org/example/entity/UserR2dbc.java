package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("users")
public class UserR2dbc {
    @Id
    private Long id;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
}
