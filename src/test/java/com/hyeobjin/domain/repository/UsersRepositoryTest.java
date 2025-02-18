package com.hyeobjin.domain.repository;

import com.hyeobjin.application.common.dto.register.RegisterDTO;
import com.hyeobjin.domain.entity.users.Users;
import com.hyeobjin.domain.repository.users.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersRepositoryTest {

    @Autowired
    UsersRepository usersRepository;
    
    @Test
    @DisplayName("UserRepository connection test")
    void findByAllUsers() {
        List<Users> usersList = usersRepository.findAll();
        usersList.stream().forEach(System.out::println);
        assertThat(usersList).isNotNull();
    }

    @Test
    @DisplayName("User Register test")
    void registerTest() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("king00314@naver.com");
        registerDTO.setPassword("1234");
        registerDTO.setUserMail("king00314@gmail.com");
        registerDTO.setUserTel("010-5507-2536");

        Users users = new Users()
                .registerData(registerDTO, registerDTO.getPassword());
        Users saveUsers = usersRepository.save(users);

        assertThat(users).isEqualTo(saveUsers);
    }

    @Test
    @DisplayName("회원의 아이디를 통해 해당 회원 엔티티 객체 조회")
    void findById() {
        String username = "alsrb362@daum.net";
        Users users = usersRepository.findByUsername(username);
        System.out.println("users.getUsername() = " + users.getUsername());
        System.out.println("users.getPassword() = " + users.getPassword());
    }

    @Test
    @DisplayName("회원의 아이디를 입력하여 해당 회원의 pk 를 조회")
    void findByIdUsername() {

        Long usersId = usersRepository.findByIdByUsername("king00314@naver.com");
        assertThat(usersId).isEqualTo(1L);
    }

    @Test
    @DisplayName("모든 관리자의 이메일 조회")
    void findByAllUsersEmail() {
        List<String> allByUserMail = usersRepository.findAllByUserMail();

        System.out.println("allByUserMail = " + allByUserMail);
    }
}