package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.GiftCertificateDao;
import com.epam.esm.gift_system.repository.dao.TagDao;
import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.service.GiftCertificateService;
import com.epam.esm.gift_system.service.converter.DtoToGiftCertificateAttributeConverter;
import com.epam.esm.gift_system.service.converter.DtoToGiftCertificateConverter;
import com.epam.esm.gift_system.service.converter.DtoToTagConverter;
import com.epam.esm.gift_system.service.converter.GiftCertificateToDtoConverter;
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
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.gift_system.service.exception.ErrorCode.*;
import static com.epam.esm.gift_system.service.validator.EntityValidator.ValidationType.INSERT;
import static com.epam.esm.gift_system.service.validator.EntityValidator.ValidationType.UPDATE;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final EntityValidator validator;
    private final TagDao tagDao;
    private final GiftCertificateDao certificateDao;
    private final DtoToTagConverter toTagConverter;
    private final DtoToGiftCertificateConverter toCertificateConverter;
    private final GiftCertificateToDtoConverter toCertificateDtoConverter;
    private final DtoToGiftCertificateAttributeConverter toCertificateAttributeConverter;

    @Autowired
    public GiftCertificateServiceImpl(EntityValidator validator, TagDao tagDao, GiftCertificateDao certificateDao
            , DtoToTagConverter toTagConverter, DtoToGiftCertificateConverter toCertificateConverter
            , GiftCertificateToDtoConverter toCertificateDtoConverter, DtoToGiftCertificateAttributeConverter toCertificateAttributeConverter) {
        this.validator = validator;
        this.tagDao = tagDao;
        this.certificateDao = certificateDao;
        this.toTagConverter = toTagConverter;
        this.toCertificateConverter = toCertificateConverter;
        this.toCertificateDtoConverter = toCertificateDtoConverter;
        this.toCertificateAttributeConverter = toCertificateAttributeConverter;
    }

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto certificateDto) {
        checkCertificateValidation(certificateDto, INSERT);
        GiftCertificate certificate = toCertificateConverter.convert(certificateDto);
        Set<Tag> tagSet = findOrCreateTags(certificate.getTags());
        certificate.setTags(tagSet);
        certificateDao.create(certificate);
        return toCertificateDtoConverter.convert(certificate);
    }

    private Set<Tag> findOrCreateTags(Set<Tag> tagSet) {
        return tagSet.stream().map(tagDao::findOrCreateTag).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    @Transactional
    public GiftCertificateDto update(Long id, GiftCertificateDto certificateDto) {
        checkCertificateValidation(certificateDto, UPDATE);
        GiftCertificate persistedCertificate = findCertificateById(id);
        setUpdatedFields(persistedCertificate, certificateDto);
        setUpdatedTags(persistedCertificate, certificateDto.getTags());
        certificateDao.update(persistedCertificate);
        return toCertificateDtoConverter.convert(persistedCertificate);
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
        if (!validator.isTagListValid(certificateDto.getTags(), type)) {
            throw new GiftSystemException(INVALID_NAME);
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

    private void setUpdatedTags(GiftCertificate persistedCertificate, List<TagDto> tagDtoList) {
        if (!CollectionUtils.isEmpty(tagDtoList)) {
            Set<Tag> updatedTagSet = tagDtoList.stream()
                    .map(toTagConverter::convert)
                    .map(tagDao::findOrCreateTag)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            persistedCertificate.setTags(updatedTagSet);
        }
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        return toCertificateDtoConverter.convert(findCertificateById(id));
    }

    private GiftCertificate findCertificateById(Long id) {
        return certificateDao.findById(id).orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY));
    }

    @Override
    public List<GiftCertificateDto> findByAttributes(GiftCertificateAttributeDto attributeDto) {
        if (validator.isAttributeListValid(attributeDto)) {
            GiftCertificateAttribute attribute = toCertificateAttributeConverter.convert(attributeDto);
            List<GiftCertificate> certificateList = certificateDao.findByAttributes(attribute);
            return certificateList.stream().map(toCertificateDtoConverter::convert).toList();
        }
        throw new GiftSystemException(INVALID_ATTRIBUTE_LIST);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        certificateDao.delete(findCertificateById(id));
    }
}