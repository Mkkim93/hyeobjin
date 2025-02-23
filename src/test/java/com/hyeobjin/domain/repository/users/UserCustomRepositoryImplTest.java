package com.hyeobjin.domain.repository.users;

import com.hyeobjin.application.admin.dto.users.UpdateUserDTO;
import com.hyeobjin.domain.entity.users.Users;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
@Transactional
@Import(UsersCustomRepositoryImpl.class)
@DisplayName("관리자 상세 정보 테스트")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserCustomRepositoryImplTest {

    @Autowired
    private UsersCustomRepositoryImpl userCustomRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("수정 : 관리자 정보 수정")
    void updateUserProfile() {

        // given
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setUserId(1L);
        updateUserDTO.setUserTel("010-4454-2234");

        // when
        userCustomRepository.updateUsersProfile(updateUserDTO);

        entityManager.flush();
        entityManager.clear();

        Users users = usersRepository.findById(updateUserDTO.getUserId()).get();

        // then
        assertThat(users.getUserTel()).isEqualTo(updateUserDTO.getUserTel());
    }
}