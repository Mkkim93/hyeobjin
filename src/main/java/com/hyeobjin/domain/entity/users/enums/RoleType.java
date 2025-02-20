package com.hyeobjin.domain.entity.users.enums;

import lombok.Getter;

@Getter
public enum RoleType {
    ROLE_USER("사용자"),
    ROLE_ADMIN("관리자");

    private final String description;

    RoleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
