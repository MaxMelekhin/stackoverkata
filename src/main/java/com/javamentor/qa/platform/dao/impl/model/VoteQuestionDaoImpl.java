package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.Question;
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
    public Optional<VoteQuestion> getVoteQuestionByUserIdAndQuestionId(Long userId, Long questionId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                        SELECT vq
                        FROM VoteQuestion vq
                        WHERE vq.user.id = :userId AND
                        vq.question.id = :questionId
                        """,VoteQuestion.class)
                .setParameter("userId", userId)
                .setParameter("questionId", questionId));
    }

    @Override
    public Long getSumUpAndDownVotes(Question question) {
        return entityManager.createQuery("""
                SELECT SUM(CASE WHEN vote = 'UP' THEN  1
                WHEN vote = 'DOWN' THEN -1  END) FROM VoteQuestion vq
                WHERE vq.question.id = :questionId
                """, Long.class).setParameter("questionId", question.getId()).getSingleResult();
    }
}
