package com.hyeobjin.domain.entity;

import com.hyeobjin.application.dto.register.RegisterDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotEmpty
    @Column(name = "username")
    private String username;

    @NotEmpty
    @Column(name = "password")
    private String password;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    @Column(name = "user_tel")
    private String userTel;

    @Column(name = "user_mail")
    private String userMail;

    public void setCreateJwtData(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Users registerData(RegisterDTO registerDTO, String password) {
        this.username = registerDTO.getUsername();
        this.password = password;
        this.name = registerDTO.getName();
        this.role = registerDTO.getRole();
        return this;
    }
}
