package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.GiftCertificateDao;
import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.service.DtoConverterService;
import com.epam.esm.gift_system.service.EntityConverterService;
import com.epam.esm.gift_system.service.GiftCertificateService;
import com.epam.esm.gift_system.service.TagService;
import com.epam.esm.gift_system.service.dto.GiftCertificateAttributeDto;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.service.exception.GiftSystemException;
import com.epam.esm.gift_system.service.validator.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.gift_system.service.exception.ErrorCode.CERTIFICATE_INVALID_DESCRIPTION;
import static com.epam.esm.gift_system.service.exception.ErrorCode.CERTIFICATE_INVALID_DURATION;
import static com.epam.esm.gift_system.service.exception.ErrorCode.CERTIFICATE_INVALID_NAME;
import static com.epam.esm.gift_system.service.exception.ErrorCode.CERTIFICATE_INVALID_PRICE;
import static com.epam.esm.gift_system.service.exception.ErrorCode.INVALID_ATTRIBUTE_LIST;
import static com.epam.esm.gift_system.service.exception.ErrorCode.TAG_INVALID_NAME;
import static com.epam.esm.gift_system.service.exception.ErrorCode.NON_EXISTENT_ENTITY;
import static com.epam.esm.gift_system.service.exception.ErrorCode.NULLABLE_OBJECT;
import static com.epam.esm.gift_system.service.exception.ErrorCode.USED_ENTITY;
import static com.epam.esm.gift_system.service.validator.EntityValidator.ValidationType.STRONG;
import static com.epam.esm.gift_system.service.validator.EntityValidator.ValidationType.SOFT;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao certificateDao;
    private final TagService tagService;
    private final EntityValidator validator;
    private final DtoConverterService dtoConverter;
    private final EntityConverterService entityConverter;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao certificateDao, TagService tagService, EntityValidator validator
            , DtoConverterService dtoConverter, EntityConverterService entityConverter) {
        this.certificateDao = certificateDao;
        this.tagService = tagService;
        this.validator = validator;
        this.dtoConverter = dtoConverter;
        this.entityConverter = entityConverter;
    }

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto certificateDto) {
        checkCertificateValidation(certificateDto, STRONG);
        GiftCertificate certificate = entityConverter.convertDtoIntoEntity(certificateDto);
        setTagListInCertificateDto(certificate);
        certificateDao.create(certificate);
        return dtoConverter.convertEntityIntoDto(certificate);
    }

    private void setTagListInCertificateDto(GiftCertificate certificate) {
        Set<Tag> tagList = certificate.getTagList();
        tagList = tagList.stream().map(tagService::createTag).collect(Collectors.toCollection(LinkedHashSet::new));
        certificate.setTagList(tagList);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(Long id, GiftCertificateDto certificateDto) {
        checkCertificateValidation(certificateDto, SOFT);
        GiftCertificate persistedCertificate = findCertificateById(id);
        setUpdatedFields(persistedCertificate, certificateDto);
        setUpdatedTagList(persistedCertificate, certificateDto.getTagDtoList());
        certificateDao.update(persistedCertificate);
        return dtoConverter.convertEntityIntoDto(persistedCertificate);
    }

    private void checkCertificateValidation(GiftCertificateDto certificateDto, EntityValidator.ValidationType type) {
        if (Objects.isNull(certificateDto)) {
            throw new GiftSystemException(NULLABLE_OBJECT);
        }
        if (!validator.isNameValid(certificateDto.getName(), type)) {
            throw new GiftSystemException(CERTIFICATE_INVALID_NAME);
        }
        if (!validator.isDescriptionValid(certificateDto.getDescription(), type)) {
            throw new GiftSystemException(CERTIFICATE_INVALID_DESCRIPTION);
        }
        if (!validator.isPriceValid(certificateDto.getPrice(), type)) {
            throw new GiftSystemException(CERTIFICATE_INVALID_PRICE);
        }
        if (!validator.isDurationValid(certificateDto.getDuration(), type)) {
            throw new GiftSystemException(CERTIFICATE_INVALID_DURATION);
        }
        if (!validator.isTagListValid(certificateDto.getTagDtoList(), type)) {
            throw new GiftSystemException(TAG_INVALID_NAME);
        }
    }

    private void setUpdatedFields(GiftCertificate persistedCertificate, GiftCertificateDto updatedCertificateDto) {
        String name = updatedCertificateDto.getName();
        String description = updatedCertificateDto.getDescription();
        BigDecimal price = updatedCertificateDto.getPrice();
        int duration = updatedCertificateDto.getDuration();

        if (Objects.nonNull(name) && !persistedCertificate.getName().equals(name)) {
            persistedCertificate.setName(name);
        }
        if (Objects.nonNull(description) && !persistedCertificate.getDescription().equals(description)) {
            persistedCertificate.setDescription(description);
        }
        if (Objects.nonNull(price) && !persistedCertificate.getPrice().equals(price)) {
            persistedCertificate.setPrice(price);
        }
        if (duration != 0 && persistedCertificate.getDuration() != duration) {
            persistedCertificate.setDuration(duration);
        }
    }

    private void setUpdatedTagList(GiftCertificate persistedCertificate, List<TagDto> tagDtoList) {
        if (CollectionUtils.isEmpty(tagDtoList)) {
            return;
        }
        Set<Tag> updatedTagSet = tagDtoList.stream()
                .map(tagService::create)
                .map(entityConverter::convertDtoIntoEntity)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        persistedCertificate.setTagList(updatedTagSet);
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        return dtoConverter.convertEntityIntoDto(findCertificateById(id));
    }

    @Override
    public GiftCertificate findCertificateById(Long id) {
        return certificateDao.findById(id).orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY));
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        throw new UnsupportedOperationException("findAll method isn't implemented in GiftCertificateServiceImpl class");
    }

    @Override
    public List<GiftCertificateDto> findByAttributes(GiftCertificateAttributeDto attributeDto) {
        if (validator.isAttributeListValid(attributeDto)) {
            GiftCertificateAttribute attribute = entityConverter.convertDtoIntoEntity(attributeDto);
            List<GiftCertificate> certificateList = certificateDao.findByAttributes(attribute);
            return certificateList.stream().map(dtoConverter::convertEntityIntoDto).toList();
        }
        throw new GiftSystemException(INVALID_ATTRIBUTE_LIST);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        GiftCertificate deletedCertificate = findCertificateById(id);
        if (certificateDao.isGiftCertificateUsedInOrders(id)) {
            throw new GiftSystemException(USED_ENTITY);
        }
        certificateDao.delete(deletedCertificate);
    }
}