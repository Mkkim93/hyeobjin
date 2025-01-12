package com.hyeobjin.application.service.login;

import com.hyeobjin.domain.entity.users.Users;
import com.hyeobjin.domain.repository.users.UsersRepository;
import com.hyeobjin.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final BCryptPasswordEncoder encoder;
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users userData = usersRepository.findByUsername(username);

        if (userData == null) {
            throw new BadCredentialsException("존재하지 않는 사용자 아이디 입니다.");
        }

        if (userData != null) {
                return new CustomUserDetails(userData);
        }
        throw new UsernameNotFoundException("User not found with username");
    }
}
