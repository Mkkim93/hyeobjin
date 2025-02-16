package com.hyeobjin.domain.repository.users;

import com.hyeobjin.application.admin.dto.users.UpdateUserDTO;
import com.hyeobjin.domain.entity.users.Users;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class UserCustomRepositoryImplTest {

    @Autowired
    UsersCustomRepositoryImpl userCustomRepository;

    @Autowired
    UsersRepository usersRepository;

    @Test
    @DisplayName("유저 정보 수정")
    void updateUserProfile() {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setUserId(1L);
        updateUserDTO.setUserTel("010-4454-2234");

        userCustomRepository.updateUsersProfile(updateUserDTO);

        Users users = usersRepository.findById(updateUserDTO.getUserId()).get();

        assertThat(users.getUserTel()).isEqualTo(updateUserDTO.getUserTel());
    }

}