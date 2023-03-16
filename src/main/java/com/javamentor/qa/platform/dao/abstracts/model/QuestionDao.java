package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;

import java.util.Optional;

public interface QuestionDao extends ReadWriteDao<Question, Long> {

    Optional<Long> getCountQuestion();

    Optional<Question> getQuestionByQuestionIdAndUserId(Long questionId, Long userId);
}
