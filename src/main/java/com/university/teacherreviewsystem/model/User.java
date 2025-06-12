package com.university.teacherreviewsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;

    private String email;

    private String password;

    @Column(name = "full_name")
    private String fullname;

    @Enumerated(EnumType.STRING)
    private Role role;
}
