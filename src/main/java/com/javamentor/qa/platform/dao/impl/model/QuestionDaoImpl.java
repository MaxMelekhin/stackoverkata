package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class QuestionDaoImpl extends ReadWriteDaoImpl<Question, Long> implements QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long getCountQuestion() {
        return entityManager.createQuery("""
                                
                select count(*) from Question
                                
                """, Long.class).getSingleResult();
    }

    @Override
    public Optional<Question> getQuestionById(Long id, Long userId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                        from Question q 
                        where q.id = :id 
                        and q.user.id != :userId
                        """, Question.class)
                .setParameter("id", id)
                .setParameter("userId", userId));
    }
}
