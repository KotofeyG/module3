package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.TagDao;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.service.DtoConverterService;
import com.epam.esm.gift_system.service.EntityConverterService;
import com.epam.esm.gift_system.service.TagService;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.service.exception.GiftSystemException;
import com.epam.esm.gift_system.service.validator.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.gift_system.service.exception.ErrorCode.TAG_INVALID_NAME;
import static com.epam.esm.gift_system.service.exception.ErrorCode.NON_EXISTENT_ENTITY;
import static com.epam.esm.gift_system.service.exception.ErrorCode.USED_ENTITY;
import static com.epam.esm.gift_system.service.validator.EntityValidator.ValidationType.STRONG;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final EntityValidator validator;
    private final DtoConverterService dtoConverter;
    private final EntityConverterService entityConverterService;

    @Autowired
    public TagServiceImpl(TagDao tagDao, EntityValidator validator, DtoConverterService dtoConverter
            , EntityConverterService entityConverterService) {
        this.tagDao = tagDao;
        this.validator = validator;
        this.dtoConverter = dtoConverter;
        this.entityConverterService = entityConverterService;
    }

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        return dtoConverter.convertEntityIntoDto(createTag(entityConverterService.convertDtoIntoEntity(tagDto)));
    }

    @Override
    @Transactional
    public Tag createTag(Tag tag) {
        if (validator.isNameValid(tag.getName(), STRONG)) {
            Optional<Tag> optionalTag = tagDao.findByName(tag.getName());
            return optionalTag.orElseGet(() -> tagDao.create(tag));
        }
        throw new GiftSystemException(TAG_INVALID_NAME);
    }

    @Override
    public TagDto update(Long id, TagDto tagDto) {
        throw new UnsupportedOperationException("update method isn't implemented in TagServiceImpl class");
    }

    @Override
    public TagDto findById(Long id) {
        return dtoConverter.convertEntityIntoDto(findTagById(id));
    }

    private Tag findTagById(Long id) {
        return tagDao.findById(id).orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY));
    }

    @Override
    public List<TagDto> findAll() {
        return tagDao.findAll().stream().map(dtoConverter::convertEntityIntoDto).toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tag deleted = findTagById(id);
        if (tagDao.isTagUsedInCertificates(id)) {
            throw new GiftSystemException(USED_ENTITY);
        }
        tagDao.delete(deleted);
    }
}