package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

@Override
public Optional<List<TagDto>> getTagsByQuestionId(Long questionId) {
    List<TagDto> list = entityManager.createQuery("""
                    select new com.javamentor.qa.platform.models.dto.TagDto(
                        t.id,
                        t.name,
                        t.description
                    )
                    from Question as q
                    join q.tags as t
                    where q.id = :questionId
                    """, TagDto.class)
            .setParameter("questionId", questionId)
            .getResultList();
    return Optional.of(list);
}
}
