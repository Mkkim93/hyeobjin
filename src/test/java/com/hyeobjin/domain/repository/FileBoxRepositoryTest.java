package com.hyeobjin.domain.repository;

import com.hyeobjin.domain.entity.FileBox;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FileBoxRepositoryTest {

    @Autowired FileBoxRepository fileBoxRepository;

    @Test
    @DisplayName("FileBoxRepository test")
    void test() {
        List<FileBox> result = fileBoxRepository.findAll();
        result.stream().forEach(System.out::println);
        Assertions.assertThat(result).isNull();
    }
}