package com.hyeobjin.application.admin.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {

    private Long userId;
    private String password;
    private String username;
    private String name;
    private String userTel;
    private String userEmail;

}
