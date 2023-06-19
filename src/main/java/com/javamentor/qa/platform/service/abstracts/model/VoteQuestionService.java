package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.Optional;

public interface VoteQuestionService extends ReadWriteService<VoteQuestion, Long> {
    Long getAllVotesForQuestion(Long questionId);

    void voteUpQuestion(User sender, Question question);

    void createVoteDownQuestion(User user, Question question, VoteType voteType);

    Optional<VoteQuestion> getVoteQuestionByUserIdAndQuestionId(Long userId, Long questionId);

    Long getSumUpAndDownVotes(Question question);
}
