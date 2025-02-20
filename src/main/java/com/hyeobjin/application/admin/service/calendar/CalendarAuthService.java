package com.hyeobjin.application.admin.service.calendar;

import com.hyeobjin.domain.repository.users.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarAuthService {

    private final UsersRepository usersRepository;

    public Long findUserId(String username) {

        return usersRepository.findIdByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for username: " + username));
    }
}
