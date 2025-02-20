package com.hyeobjin.application.admin.service.auth;

import com.hyeobjin.application.admin.dto.users.CheckUserDTO;
import com.hyeobjin.application.admin.dto.users.UpdateUserDTO;
import com.hyeobjin.domain.entity.users.Users;
import com.hyeobjin.domain.repository.users.UsersCustomRepositoryImpl;
import com.hyeobjin.domain.repository.users.UsersRepository;
import com.hyeobjin.jwt.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final UsersRepository usersRepository;
    private final UsersCustomRepositoryImpl usersCustomRepositoryImpl;
    private final BCryptPasswordEncoder passwordEncoder;

    public void update(UpdateUserDTO updateUserDTO) {
        usersCustomRepositoryImpl.updateUsersProfile(updateUserDTO);
    }

    public boolean checkUserInfo(String username, String existPassword) {
        // 1️⃣ 사용자 정보 조회 (Optional 사용)
        Users userEntity = usersRepository.findByUsername(username);

        boolean isMatch = passwordEncoder.matches(existPassword, userEntity.getPassword());

        if (!isMatch) {
            log.warn("비밀번호 불일치: 입력한 비밀번호={}, 저장된 비밀번호={}", existPassword, userEntity.getPassword());
            return false;
        }

        log.info("비밀번호 일치: {}", username);
        return true;
    }


    @Transactional
    public boolean updatePassword(String username, String newPassword) {

        String updateEncodedPassword = passwordEncoder.encode(newPassword);

        Long userId = usersRepository.findIdByUsername(username).orElseThrow(() -> new EntityNotFoundException("사용자 정보를 찾는 도중 오류가 발생하였습니다."));

        int exist = usersRepository.updatePassword(updateEncodedPassword, userId);

        if (exist != 1) {
            return false;
        }

        return true;
    }

    public CheckUserDTO findMyProfile(String username, String password) {

        String authUsername = SecurityContextHolder.getContext().getAuthentication().getName();

//        String authUsername = "king00314@naver.com";

        if (!username.equals(authUsername)) {
            throw new RuntimeException("입력한 사용자 정보가 서버에 저장된 정보와 일치하지 않습니다.");
        }
        Users users = usersRepository.findByUsername(username);

        boolean matches = passwordEncoder.matches(password, users.getPassword());

        if (!matches) {
            throw new RuntimeException("입력한 비밀번호가 일치 하지 않습니다.");
        }

        CheckUserDTO checkUserDTO = new CheckUserDTO().toDto(users);

        return checkUserDTO;
    }
}
