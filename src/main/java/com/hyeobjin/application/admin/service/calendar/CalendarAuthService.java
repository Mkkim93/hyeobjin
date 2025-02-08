package com.hyeobjin.application.admin.service.calendar;

import com.hyeobjin.domain.repository.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarAuthService {

    private final UsersRepository usersRepository;

    public Long findUserId(String username) {
        return usersRepository.findByIdByUsername(username);
    }
}
