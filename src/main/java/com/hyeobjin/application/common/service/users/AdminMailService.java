package com.hyeobjin.application.common.service.users;

import com.hyeobjin.domain.repository.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminMailService {

    private final UsersRepository usersRepository;

    public List<String> findAdminMailList() {
        return usersRepository.findAllByUserMail();
    }
}
