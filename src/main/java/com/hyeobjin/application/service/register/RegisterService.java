package com.hyeobjin.application.service.register;

import com.hyeobjin.application.dto.register.RegisterDTO;
import com.hyeobjin.domain.entity.Users;
import com.hyeobjin.domain.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 회원 가입 로직 (현재는 관리자 등록)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Boolean register(RegisterDTO registerDTO) {
        String username = registerDTO.getUsername();
        String password = registerDTO.getPassword();
        Boolean isExist = usersRepository.existsByUsername(username);

        if (isExist) {
            log.info("이미 존재하는 ID 입니다.");
            return false;
        }

        Users users = new Users();
        users.registerData(registerDTO, passwordEncoder.encode(password));

        usersRepository.save(users);
        log.info("success register, name={}", users.getName());
        return true;
    }
}
