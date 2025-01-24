package com.hyeobjin.application.common.dto.register;

import lombok.Data;

@Data
public class RegisterDTO {

    private String username;
    private String password;
    private String name;

    private String userTel;
    private String userMail;
}
