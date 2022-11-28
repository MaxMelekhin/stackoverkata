package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TagDtoServiceImpl implements TagDtoService {
  private final TagDtoDao tagDtoDao;

  public TagDtoServiceImpl(TagDtoDao tagDtoDao) {
    this.tagDtoDao = tagDtoDao;
  }

  @Override
  public List<IgnoredTagDto> getAllIgnoredTags(Long userId) {
    return tagDtoDao.getAllIgnoredTags(userId);
  }

}
