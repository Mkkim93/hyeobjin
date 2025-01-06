package com.hyeobjin.domain.repository;

import com.hyeobjin.domain.entity.Users;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersRepositoryTest {

    @Autowired
    UsersRepository usersRepository;
    
    @Test
    @DisplayName("UserRepository test")
    void findByAllUsers() {
        List<Users> usersList = usersRepository.findAll();
        usersList.stream().forEach(System.out::println);
        Assertions.assertThat(usersList).isNotNull();
    }
}