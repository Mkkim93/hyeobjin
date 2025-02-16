package com.hyeobjin.domain.repository.users;

import com.hyeobjin.application.admin.dto.users.UpdateUserDTO;

public interface CustomUsersRepository {

    void updateUsersProfile(UpdateUserDTO updateUserDTO);
}
