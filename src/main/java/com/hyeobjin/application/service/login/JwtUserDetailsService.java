package com.hyeobjin.application.service.login;

import com.hyeobjin.domain.entity.Users;
import com.hyeobjin.domain.repository.UsersRepository;
import com.hyeobjin.jwt.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public JwtUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users userData = usersRepository.findByUsername(username);

        log.info("userData username={}", userData.getUsername());
        log.info("userData password={}", userData.getPassword());
        log.info("userData role={}", userData.getRole());

        if (userData != null) {
            return new CustomUserDetails(userData);
        }

        throw new UsernameNotFoundException("User not found with username");
    }
}
