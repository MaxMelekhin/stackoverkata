package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.exception.VoteException;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {

    private final ReputationService reputationService;
    private final VoteQuestionDao voteQuestionDao;

    private final QuestionService questionService;

    public VoteQuestionServiceImpl(ReadWriteDao<VoteQuestion, Long> readWriteDao,
                                   ReputationService reputationService, VoteQuestionDao voteQuestionDao,
                                   QuestionService questionService) {
        super(readWriteDao);
        this.reputationService = reputationService;
        this.voteQuestionDao = voteQuestionDao;
        this.questionService = questionService;
    }

    @Transactional
    @Override
    public void voteUpQuestion(User sender, Question question) {
        VoteQuestion voteQuestion;
        Optional<VoteQuestion> optionalVoteQuestion = voteQuestionDao.getVoteByUserIdAndQuestionId(sender.getId(), question.getId());
        if (optionalVoteQuestion.isEmpty()) {
            voteQuestion = new VoteQuestion(sender, question, VoteType.UP);
            voteQuestionDao.persist(voteQuestion);
        } else {
            voteQuestionDao.update(optionalVoteQuestion.get());
        }
    }
    @Override
    @Transactional
    public void createVoteDownQuestion(User user, Question question, VoteType voteType) {
        Reputation reputation;

        if (questionService.getById(question.getId()).get().getUser().getId().equals(user.getId())) {
            throw new VoteException("Пользователь не может голосовать за свой вопрос");
        }

        if (getVoteQuestionByUserIdAndQuestionId(user.getId(), question.getId()).isEmpty()) {
            VoteQuestion newVoteQuestion = new VoteQuestion(user, question, voteType);
            persist(newVoteQuestion);
        } else {
            VoteQuestion voteQuestion = getVoteQuestionByUserIdAndQuestionId(user.getId(), question.getId()).get();
            switch (voteQuestion.getVote().name()) {
                case "DOWN" -> throw new VoteException("Пользователь голосовал против ранее");
                case "UP" -> {
                    voteQuestion.setVote(voteType);
                    update(voteQuestion);
                }
            }
        }
        if (reputationService.getReputationByUserIdQuestionIdReputationType(question.getUser().getId(), question.getId(),
                ReputationType.VoteQuestion).isEmpty()) {
            reputation = new Reputation();
            reputation.setPersistDate(LocalDateTime.now());
            reputation.setQuestion(question);
            reputation.setAuthor(question.getUser());
            reputation.setType(ReputationType.VoteQuestion);
            reputation.setCount(-5);
            reputationService.persist(reputation);
        } else {
            reputation = reputationService.getReputationByUserIdQuestionIdReputationType(question.getUser().getId(), question.getId(),
                    ReputationType.VoteQuestion).get();
            reputation.setCount(reputation.getCount() - 5);
            reputationService.update(reputation);
        }
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<VoteQuestion> getVoteQuestionByUserIdAndQuestionId(Long userId, Long questionId) {
        return voteQuestionDao.getVoteQuestionByUserIdAndQuestionId(userId, questionId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getSumUpAndDownVotes(Question question) {
        return voteQuestionDao.getSumUpAndDownVotes(question);
    }
    @Override
    public Long getAllVotesForQuestion(Long questionId) {
        return voteQuestionDao.getAllVotesForQuestion(questionId);
    }

}



