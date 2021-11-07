package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.TagDao;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.service.DtoConverterService;
import com.epam.esm.gift_system.service.EntityConverterService;
import com.epam.esm.gift_system.service.TagService;
import com.epam.esm.gift_system.service.dto.CustomPage;
import com.epam.esm.gift_system.service.dto.CustomPageable;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.service.exception.GiftSystemException;
import com.epam.esm.gift_system.service.validator.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.gift_system.service.exception.ErrorCode.INVALID_DATA_OF_PAGE;
import static com.epam.esm.gift_system.service.exception.ErrorCode.NON_EXISTENT_PAGE;
import static com.epam.esm.gift_system.service.exception.ErrorCode.TAG_INVALID_NAME;
import static com.epam.esm.gift_system.service.exception.ErrorCode.NON_EXISTENT_ENTITY;
import static com.epam.esm.gift_system.service.exception.ErrorCode.USED_ENTITY;
import static com.epam.esm.gift_system.service.validator.EntityValidator.ValidationType.STRONG;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final EntityValidator validator;
    private final DtoConverterService dtoConverter;
    private final EntityConverterService entityConverter;

    @Autowired
    public TagServiceImpl(TagDao tagDao, EntityValidator validator, DtoConverterService dtoConverter
            , EntityConverterService entityConverter) {
        this.tagDao = tagDao;
        this.validator = validator;
        this.dtoConverter = dtoConverter;
        this.entityConverter = entityConverter;
    }

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        Tag created = createTag(entityConverter.convertDtoIntoEntity(tagDto));
        return dtoConverter.convertEntityIntoDto(createTag(created));
    }

    @Override
    @Transactional
    public Tag createTag(Tag tag) {
        if (validator.isNameValid(tag.getName(), STRONG)) {
            return tagDao.findByName(tag.getName()).orElseGet(() -> tagDao.create(tag));
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
    public CustomPage<TagDto> findAll(CustomPageable pageable) {
        if (!validator.isPageDataValid(pageable)) {
            throw new GiftSystemException(INVALID_DATA_OF_PAGE);
        }
        long totalTagNumber = tagDao.findEntityNumber();
        if (!validator.isPageExists(pageable, totalTagNumber)) {
            throw new GiftSystemException(NON_EXISTENT_PAGE);
        }
        int offset = calculateOffset(pageable);
        List<TagDto> tagDtoList = tagDao.findAll(offset, pageable.getSize())
                .stream().map(dtoConverter::convertEntityIntoDto).toList();
        return new CustomPage<>(tagDtoList, pageable, totalTagNumber);
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