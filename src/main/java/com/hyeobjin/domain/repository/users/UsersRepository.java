package com.hyeobjin.domain.repository.users;


import com.hyeobjin.domain.entity.users.Users;
import com.hyeobjin.domain.entity.users.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("select u.id from Users u where u.username = :username")
    Optional<Long> findIdByUsername(@Param("username") String username);

    @Query("select u.userMail from Users u")
    List<String> findAllByUserMail();

    @Modifying
    @Query("update Users u set u.role = :roleType where u.id = :usersId")
    int updateUsersRoleType(@Param("roleType") RoleType roleType, @Param("usersId") Long usersId);

    @Modifying
    @Query("update Users u set u.password = :password where u.id = :usersId")
    int updatePassword(@Param("password") String password, @Param("usersId") Long usersId);
}
