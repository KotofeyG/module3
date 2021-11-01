package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.TagDao;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.service.ConverterService;
import com.epam.esm.gift_system.service.TagService;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.service.exception.GiftSystemException;
import com.epam.esm.gift_system.service.validator.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.gift_system.service.exception.ErrorCode.INVALID_NAME;
import static com.epam.esm.gift_system.service.exception.ErrorCode.NON_EXISTENT_ENTITY;
import static com.epam.esm.gift_system.service.exception.ErrorCode.USED_ENTITY;
import static com.epam.esm.gift_system.service.validator.EntityValidator.ValidationType.INSERT;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final EntityValidator validator;
    private final ConverterService converter;

    @Autowired
    public TagServiceImpl(TagDao tagDao, EntityValidator validator, ConverterService converter) {
        this.tagDao = tagDao;
        this.validator = validator;
        this.converter = converter;
    }

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        if (validator.isNameValid(tagDto.getName(), INSERT)) {
            Optional<Tag> optionalTag = tagDao.findByName(tagDto.getName());
            return optionalTag.isPresent()
                    ? converter.convertEntityIntoDto(optionalTag.get())
                    : converter.convertEntityIntoDto(tagDao.create(converter.convertDtoIntoEntity(tagDto)));
        }
        throw new GiftSystemException(INVALID_NAME);
    }

    @Override
    public TagDto update(Long id, TagDto tagDto) {
        throw new UnsupportedOperationException("update method isn't implemented in TagServiceImpl class");
    }

    @Override
    public TagDto findById(Long id) {
        return converter.convertEntityIntoDto(findTagById(id));
    }

    private Tag findTagById(Long id) {
        return tagDao.findById(id).orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY));
    }

    @Override
    public List<TagDto> findAll() {
        return tagDao.findAll().stream().map(converter::convertEntityIntoDto).collect(Collectors.toList());
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