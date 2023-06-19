package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;

import java.util.Optional;

public interface ReputationDao extends ReadWriteDao<Reputation, Long> {

    Optional<Reputation> getReputationByUserIdQuestionIdReputationType(Long userId, Long questionId, ReputationType reputationType);

    Optional<Reputation> getReputationByAnswerIdAndUserId(Long senderId, Long answerId);
    Optional<Reputation> reputationExists (User sender, Long answerId);
}
