package com.hyeobjin.application.admin.service.users;

import com.hyeobjin.application.admin.dto.users.FindUsersDTO;
import com.hyeobjin.application.common.dto.register.RegisterDTO;
import com.hyeobjin.domain.entity.users.Users;
import com.hyeobjin.domain.repository.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Page<FindUsersDTO> findAll(Pageable pageable) {

        Page<Users> usersList = usersRepository.findAll(pageable);

        return usersList.map(users -> new FindUsersDTO(
                users.getId(),
                users.getUsername(),
                users.getName(),
                users.getRole(),
                users.getUserTel(),
                users.getUserMail()
        ));
    }

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
