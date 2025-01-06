package com.hyeobjin.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class Users {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;
}
