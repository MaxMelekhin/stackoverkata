package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class VoteQuestionDaoImpl extends ReadWriteDaoImpl<VoteQuestion, Long> implements VoteQuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long getAllVotesForQuestion(Long questionId) {
        return entityManager.createQuery("""
                        select count (v.id) 
                        from VoteQuestion v 
                        where v.question.id =: questionId
                        """, Long.class)
                .setParameter("questionId", questionId)
                .getSingleResult();
    }

    @Override
    public Optional<VoteQuestion> getVoteQuestionByUserIdAndQuestionId(Long userId, Long questionId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                        from VoteQuestion v 
                        where v.question.id  =: questionId
                        and v.user.id =: userId
                        and v.vote = 'UP' 
                        """, VoteQuestion.class)
                .setParameter("userId", userId)
                .setParameter("questionId", questionId));
    }
}
