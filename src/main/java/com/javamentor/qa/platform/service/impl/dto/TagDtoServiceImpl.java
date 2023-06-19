package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagDtoServiceImpl implements TagDtoService {
private final TagDtoDao tagDtoDao;

public TagDtoServiceImpl(TagDtoDao tagDtoDao) {
    this.tagDtoDao = tagDtoDao;
}

@Override
@Transactional(readOnly = true)
public Optional<List<TagDto>> getTagsByQuestionId(Long questionId) {
    return tagDtoDao.getTagsByQuestionId(questionId);
}
}
