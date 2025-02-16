package com.hyeobjin.application.admin.dto.users;

import com.hyeobjin.domain.entity.users.Users;
import com.hyeobjin.domain.entity.users.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindUsersDTO {

    private Long usersId;
    private String username;

    private String name;
    private RoleType role;

    private String userTel;
    private String userMail;

    public FindUsersDTO toEntity(Users users) {
        this.usersId = users.getId();
        this.username = users.getUsername();
        this.name = users.getName();
        this.role = users.getRole();
        this.userTel = users.getUserTel();
        this.userMail = users.getUserMail();
        return this;
    }

}
