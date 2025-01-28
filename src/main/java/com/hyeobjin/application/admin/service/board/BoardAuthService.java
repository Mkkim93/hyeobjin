package com.hyeobjin.application.admin.service.board;

import com.hyeobjin.domain.repository.users.UsersRepository;
import com.hyeobjin.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardAuthService {

    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;

    public Long findByUserId(String authToken) {
        String username = jwtUtil.getUsername(authToken);
        return usersRepository.findByIdByUsername(username);
    }
}
