package com.hyeobjin.application.admin.dto.users;

import com.hyeobjin.domain.entity.users.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckUserDTO {

    private Long userId;
    private String username;
    private String password;
    private String name;
    private String userTel;
    private String userMail;
    private String role;

    public CheckUserDTO toDto(Users users) {
        this.userId = users.getId();
        this.username = users.getUsername();
        this.name = users.getName();
        this.userTel = users.getUserTel();
        this.userMail = users.getUserMail();
        this.role = users.getRole().name();
        return this;
    }
}
