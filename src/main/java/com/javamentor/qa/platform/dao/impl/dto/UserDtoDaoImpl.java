package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<UserDto> getById(Long id) {
        TypedQuery<UserDto> query = entityManager.createQuery("""
                select new com.javamentor.qa.platform.models.dto.UserDto (
                    u.id,
                    u.email,
                    u.fullName,
                    u.imageLink as linkImage,
                    u.city,
                    COALESCE((select sum(r.count) from Reputation r where r.author.id = u.id), 0) as reputation,
                    u.persistDateTime as registrationDate,
                    COALESCE((select count(*) from VoteQuestion v where v.user.id = u.id), 0) +
                    COALESCE((select count(*) from VoteAnswer v where v.user.id = u.id), 0) as votes
                )
                from User u
                where u.id = :id
                """, UserDto.class);
        query.setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}