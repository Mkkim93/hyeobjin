package com.hyeobjin.domain.repository;

import com.hyeobjin.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByUsername(String username);

    Boolean existsByUsername(String username);
}
