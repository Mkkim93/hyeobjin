package com.hyeobjin.domain.repository.users;


import com.hyeobjin.application.admin.dto.users.UpdateUserDTO;
import com.hyeobjin.domain.entity.users.QUsers;
import com.hyeobjin.domain.entity.users.Users;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@Transactional
public class UsersCustomRepositoryImpl extends QuerydslRepositorySupport implements CustomUsersRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public UsersCustomRepositoryImpl(EntityManager entityManager) {
        super(Users.class);
        jpaQueryFactory = new JPAQueryFactory(entityManager);

    }

    @Override
    public void updateUsersProfile(UpdateUserDTO updateUserDTO) {

        QUsers users = QUsers.users;

        EntityManager entityManager = getEntityManager();

        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, users);

        if (updateUserDTO.getUsername() != null) {
            updateClause.set(users.username, updateUserDTO.getUsername());
        }

        if (updateUserDTO.getName() != null) {
            updateClause.set(users.name, updateUserDTO.getName());
        }

        if (updateUserDTO.getUserEmail() != null) {
            updateClause.set(users.userMail, updateUserDTO.getUserEmail());
        }

        if (updateUserDTO.getUserTel() != null) {
            updateClause.set(users.userTel, updateUserDTO.getUserTel());
        }

        updateClause.where(users.id.eq(updateUserDTO.getUserId()));
        updateClause.execute();
    }
}
