package com.hyeobjin.domain.entity.users;

import com.hyeobjin.application.common.dto.register.RegisterDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

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

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    @Column(name = "user_tel")
    private String userTel;

    @Column(name = "user_mail")
    private String userMail;

    @Builder
    public Users(Long userId) {
        this.id = userId;
    }

    public void setCreateJwtData(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public Users registerData(RegisterDTO registerDTO, String password) {
        this.username = registerDTO.getUsername();
        this.password = password;
        this.name = registerDTO.getName();
        this.role = "ROLE_ADMIN";
        return this;
    }
}
