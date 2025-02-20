package com.hyeobjin.application.admin.service.users;

import com.hyeobjin.application.admin.dto.users.FindUsersDTO;
import com.hyeobjin.application.admin.dto.users.UpdateUserDTO;
import com.hyeobjin.application.common.dto.register.RegisterDTO;
import com.hyeobjin.domain.entity.users.Users;
import com.hyeobjin.domain.entity.users.enums.RoleType;
import com.hyeobjin.domain.repository.users.UsersCustomRepositoryImpl;
import com.hyeobjin.domain.repository.users.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리자 정보를 등록 및 수정 하기 위한 service
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUsersService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    public FindUsersDTO detail(Long usersId) {

        boolean exist = usersRepository.existsById(usersId);

        if (!exist) {
            throw new RuntimeException("유저 정보가 없습니다.");
        }
        Users users = usersRepository.findById(usersId).orElseThrow(() -> new EntityNotFoundException("해당 유저 정보를 찾는 도중 오류가 발생하였습니다."));

        FindUsersDTO findUsersDTO = new FindUsersDTO().toEntity(users);

        return findUsersDTO;
    }



    public void delete(Long usersId) {
        usersRepository.deleteById(usersId);
    }

    @Transactional
    public boolean updateRoleType(Long usersId, String roleType) {

        RoleType newRoleType;

        try {

        newRoleType = RoleType.valueOf(roleType.toUpperCase());

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("사용자 권한을 수정하는 도중 오류가 발생하였습니다.");
        }

        int updateCount = usersRepository.updateUsersRoleType(newRoleType, usersId);

        if (updateCount != 1) {
            throw new RuntimeException("사용자 권한을 수정하는 도중 오류가 발생하였습니다.");
        }

        return true;
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
}
