package com.hyeobjin.application.admin.service.board;

import com.hyeobjin.domain.repository.users.UsersRepository;
import com.hyeobjin.jwt.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardAuthService {

    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;

    public Long findByUserId(String authToken) {

        String username = jwtUtil.getUsername(authToken);

        return usersRepository.findIdByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for username: " + username));
    }
}
