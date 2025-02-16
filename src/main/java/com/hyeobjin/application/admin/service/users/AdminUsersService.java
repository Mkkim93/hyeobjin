package com.hyeobjin.application.admin.service.users;

import com.hyeobjin.application.admin.dto.users.FindUsersDTO;
import com.hyeobjin.application.admin.dto.users.UpdateUserDTO;
import com.hyeobjin.domain.entity.users.Users;
import com.hyeobjin.domain.repository.users.UsersCustomRepositoryImpl;
import com.hyeobjin.domain.repository.users.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 관리자 정보를 등록 및 수정 하기 위한 service
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUsersService {

    private final UsersRepository usersRepository;
    private final UsersCustomRepositoryImpl usersCustomRepositoryImpl;

    public FindUsersDTO detail(Long usersId) {

        boolean exist = usersRepository.existsById(usersId);

        if (!exist) {
            throw new RuntimeException("유저 정보가 없습니다.");
        }
        Users users = usersRepository.findById(usersId).orElseThrow(() -> new EntityNotFoundException("해당 유저 정보를 찾는 도중 오류가 발생하였습니다."));

        FindUsersDTO findUsersDTO = new FindUsersDTO().toEntity(users);

        return findUsersDTO;
    }

    public void update(UpdateUserDTO updateUserDTO) {
        usersCustomRepositoryImpl.updateUsersProfile(updateUserDTO);
    }

    public void delete(Long usersId) {
        usersRepository.deleteById(usersId);
    }
}
