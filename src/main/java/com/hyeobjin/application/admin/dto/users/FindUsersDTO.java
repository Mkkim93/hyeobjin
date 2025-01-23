package com.hyeobjin.application.admin.dto.users;

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
    private String role;

    private String userTel;
    private String userMail;

}
