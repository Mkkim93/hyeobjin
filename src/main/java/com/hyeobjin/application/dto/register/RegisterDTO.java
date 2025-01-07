package com.hyeobjin.application.dto.register;

import lombok.Data;

@Data
public class RegisterDTO {

    private String username;
    private String password;
    private String name;

    private String role;
    private String userTel;
    private String userMail;
}
