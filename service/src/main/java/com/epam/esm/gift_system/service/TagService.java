package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.service.dto.TagDto;

public interface TagService extends BaseService<TagDto> {
    Tag createTag(Tag tag);
}