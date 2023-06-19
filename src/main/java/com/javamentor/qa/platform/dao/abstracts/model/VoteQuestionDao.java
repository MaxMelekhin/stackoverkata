package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;

import java.util.Optional;

public interface VoteQuestionDao extends ReadWriteDao<VoteQuestion, Long> {
    Optional<VoteQuestion> getVoteQuestionByUserIdAndQuestionId(Long userId, Long questionId);
    Long getSumUpAndDownVotes(Question question);

    Long getAllVotesForQuestion(Long questionId);

    Optional<VoteQuestion> getVoteByUserIdAndQuestionId(Long userId,Long questionId);
}
