package com.hyeobjin.domain.repository.users;


import com.hyeobjin.domain.entity.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("select u.id from Users u where u.username = :username")
    Long findByIdByUsername(@Param("username") String username);  // 제대로 된 메서드 이름

    @Query("select u.userMail from Users u")
    List<String> findAllByUserMail();


}
