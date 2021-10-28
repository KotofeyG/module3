package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.TagDao;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.service.TagService;
import com.epam.esm.gift_system.service.converter.DtoToTagConverter;
import com.epam.esm.gift_system.service.converter.TagToDtoConverter;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.service.exception.*;
import com.epam.esm.gift_system.service.validator.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.gift_system.service.exception.ErrorCode.*;
import static com.epam.esm.gift_system.service.validator.EntityValidator.ValidationType.INSERT;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final EntityValidator validator;
    private final TagToDtoConverter toTagDtoConverter;
    private final DtoToTagConverter toTagConverter;

    @Autowired
    public TagServiceImpl(TagDao tagDao, EntityValidator validator, TagToDtoConverter toTagDtoConverter
            , DtoToTagConverter toTagConverter) {
        this.tagDao = tagDao;
        this.validator = validator;
        this.toTagDtoConverter = toTagDtoConverter;
        this.toTagConverter = toTagConverter;
    }

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        if (validator.isNameValid(tagDto.getName(), INSERT)) {
            if (tagDao.isNotExisting(tagDto.getName())) {
                return toTagDtoConverter.convert(tagDao.create(toTagConverter.convert(tagDto)));
            }
            throw new GiftSystemException(DUPLICATE_NAME);
        }
        throw new GiftSystemException(INVALID_NAME);
    }

    @Override
    public TagDto findById(Long id) {
        return toTagDtoConverter.convert(findTagById(id));
    }

    private Tag findTagById(Long id) {
        return tagDao.findById(id).orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY));
    }

    @Override
    public List<TagDto> findAll() {
        return tagDao.findAll().stream().map(toTagDtoConverter::convert).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tag deleted = findTagById(id);
        if (tagDao.isUsed(id)) {
            throw new GiftSystemException(USED_ENTITY);
        }
        tagDao.delete(deleted);
    }
}