package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.Optional;

public interface ReputationService extends ReadWriteService<Reputation, Long> {

    Optional<Reputation> getReputationByUserIdQuestionIdReputationType(Long userId, Long questionId, ReputationType reputationType);

    void updateCountByDown(User sender, Long answerId);

    void addReputation(User sender, Answer answer);

    void addReputationForQuestion(User sender, Question question);
}
