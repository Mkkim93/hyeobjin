package com.hyeobjin.application.service.login;

import com.hyeobjin.application.dto.login.LoginDTO;
import com.hyeobjin.domain.entity.users.Users;
import com.hyeobjin.domain.repository.users.UsersRepository;
import com.hyeobjin.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {


    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUserDetailsService jwtUserDetailsService;


    public String login(LoginDTO loginDTO) {

        // login id checked
        Users findByUsername = usersRepository.findByUsername(loginDTO.getUsername());

        if (findByUsername == null) {
            throw new BadCredentialsException("존재하지 않는 사용자 아이디 입니다.");
        }
        boolean matches = encoder.matches(loginDTO.getPassword(), findByUsername.getPassword());
        if (!matches) {
            throw new BadCredentialsException("비밀번호가 일치 하지 않습니다.");
        }

        log.info("login Pass");
        log.info("login name={}", findByUsername.getName());

        jwtUserDetailsService.loadUserByUsername(findByUsername.getUsername());
        String token = jwtUtil.createJwt(findByUsername.getUsername(), findByUsername.getRole(), 60 * 60 * 10L);

        return token;
    }

}
