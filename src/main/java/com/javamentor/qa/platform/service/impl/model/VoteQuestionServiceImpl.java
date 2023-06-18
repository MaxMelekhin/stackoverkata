package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {
    private final VoteQuestionDao voteQuestionDao;

    public VoteQuestionServiceImpl(ReadWriteDao<VoteQuestion, Long> readWriteDao, VoteQuestionDao voteQuestionDao) {
        super(readWriteDao);
        this.voteQuestionDao = voteQuestionDao;
    }

    @Override
    public Long getAllVotesForQuestion(Long questionId) {
        return voteQuestionDao.getAllVotesForQuestion(questionId);
    }

    @Transactional
    @Override
    public void voteUpQuestion(User sender, Question question) {
        VoteQuestion voteQuestion;
        Optional<VoteQuestion> optionalVoteQuestion = voteQuestionDao.getVoteQuestionByUserIdAndQuestionId(sender.getId(), question.getId());
        if (optionalVoteQuestion.isEmpty()) {
            voteQuestion = new VoteQuestion(sender, question, VoteType.UP);
            voteQuestionDao.persist(voteQuestion);
        } else {
            voteQuestionDao.update(optionalVoteQuestion.get());
        }
    }
}



