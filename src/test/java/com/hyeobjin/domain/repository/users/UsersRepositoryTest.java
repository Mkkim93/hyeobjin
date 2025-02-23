package com.hyeobjin.domain.repository.users;

import com.hyeobjin.application.common.dto.register.RegisterDTO;
import com.hyeobjin.domain.entity.users.Users;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Transactional
@DisplayName("관리자 테스트")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;
    
    @Test
    @DisplayName("조회 : 모든 관리자 목록 조회")
    void findByAllUsers() {

        // when
        List<Users> usersList = usersRepository.findAll();

        // then
        assertThat(usersList).isNotNull();
        usersList.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("등록 : 관리자 등록 테스트")
    void registerTest() {

        // given
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("king00314@naver.com");
        registerDTO.setPassword("1234");
        registerDTO.setUserMail("king00314@gmail.com");
        registerDTO.setUserTel("010-5507-2536");

        // when
        Users users = new Users()
                .registerData(registerDTO, registerDTO.getPassword());
        Users saveUsers = usersRepository.save(users);

        // then
        assertThat(users).isEqualTo(saveUsers);
    }

    @Test
    @DisplayName("조회 : 회원 계정으로 해당 객체 조회")
    void findById() {

        // given
        String username = "alsrb362@daum.net";

        // when
        Users users = usersRepository.findByUsername(username);

        // then
        assertThat(username).isEqualTo(users.getUsername());
        System.out.println("users.getUsername() = " + users.getUsername());
        System.out.println("users.getPassword() = " + users.getPassword());
    }

    @Test
    @DisplayName("조회 : 회원 계정으로 해당 회원 PK 조회")
    void findByIdUsername() {

        // given
        String username = "king00314@naver.com";

        // when
        Optional<Long> idByUsername = usersRepository.findIdByUsername(username);

        // then
        assertThat(idByUsername.get()).isEqualTo(1L);
    }

    @Test
    @DisplayName("조회 : 모든 관리자의 개인 이메일 계정 조회")
    void findByAllUsersEmail() {

        // when
        List<String> allByUserMail = usersRepository.findAllByUserMail();

        // then
        System.out.println("allByUserMail = " + allByUserMail);
    }
}