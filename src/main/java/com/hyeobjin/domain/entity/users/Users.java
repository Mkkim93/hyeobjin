package com.hyeobjin.domain.entity.users;

import com.hyeobjin.application.common.dto.register.RegisterDTO;
import com.hyeobjin.domain.entity.users.enums.RoleType;
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
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Column(name = "user_tel")
    private String userTel;

    @Column(name = "user_mail")
    private String userMail;

    @Builder
    public Users(Long userId, String name) {
        this.id = userId;
        this.name = name;
    }

    public void setCreateJwtData(String username, RoleType role) {
        this.username = username;
        this.role = role;
    }

    public Users registerData(RegisterDTO registerDTO, String password) {
        this.username = registerDTO.getUsername();
        this.password = password;
        this.name = registerDTO.getName();
        this.role = RoleType.ROLE_ADMIN;
        this.userTel = registerDTO.getUserTel();
        this.userMail = registerDTO.getUserMail();
        return this;
    }
}
