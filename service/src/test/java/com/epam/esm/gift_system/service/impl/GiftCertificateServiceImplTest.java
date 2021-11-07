package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.GiftCertificateDao;
import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.service.DtoConverterService;
import com.epam.esm.gift_system.service.EntityConverterService;
import com.epam.esm.gift_system.service.TagService;
import com.epam.esm.gift_system.service.dto.CustomPage;
import com.epam.esm.gift_system.service.dto.CustomPageable;
import com.epam.esm.gift_system.service.dto.GiftCertificateAttributeDto;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.service.exception.GiftSystemException;
import com.epam.esm.gift_system.service.validator.EntityValidator;
import com.epam.esm.gift_system.service.validator.EntityValidator.ValidationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @InjectMocks
    private GiftCertificateServiceImpl service;
    @Mock
    private GiftCertificateDao certificateDao;
    @Mock
    private TagService tagService;
    @Mock
    private EntityValidator validator;
    @Mock
    private DtoConverterService dtoConverter;
    @Mock
    private EntityConverterService entityConverter;

    private Tag tag;
    private Tag updatedTag;
    private TagDto tagDto;
    private TagDto updatedTagDto;
    private GiftCertificate certificate;
    private GiftCertificateDto updatedCertificateDto;
    private GiftCertificateDto certificateDto;
    private CustomPageable pageable;
    private CustomPage<GiftCertificateDto> page;

    @BeforeEach
    void setUp() {
        tag = Tag.builder().id(1L).name("TagName").build();
        tagDto = TagDto.builder().id(1L).name("TagName").build();
        updatedTag = Tag.builder().id(2L).name("NewTagName").build();
        updatedTagDto = TagDto.builder().id(2L).name("NewTagName").build();
        pageable = new CustomPageable();
        pageable.setPage(10);
        pageable.setSize(1);
        certificate = GiftCertificate.builder()
                .id(1L)
                .name("CertificateName")
                .description("Description")
                .price(new BigDecimal("10"))
                .duration(5)
                .createDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .lastUpdateDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .tagList(Set.of(tag))
                .build();
        updatedCertificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("NewCertificateName")
                .description("NewDescription")
                .price(new BigDecimal("20"))
                .duration(10)
                .createDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .lastUpdateDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .tagDtoList(List.of(updatedTagDto))
                .build();
        certificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("CertificateName")
                .description("Description")
                .price(new BigDecimal("10"))
                .duration(5)
                .createDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .lastUpdateDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .tagDtoList(List.of(tagDto))
                .build();
        page = new CustomPage<>(List.of(certificateDto), pageable, 30L);
    }

    @Test
    void createWithValidParam() {
        setValidCertificateParams();
        doReturn(certificate).when(entityConverter).convertDtoIntoEntity(any(GiftCertificateDto.class));
        doReturn(tag).when(tagService).createTag(any(Tag.class));
        doReturn(certificate).when(certificateDao).create(any(GiftCertificate.class));
        doReturn(certificateDto).when(dtoConverter).convertEntityIntoDto(any(GiftCertificate.class));
        GiftCertificateDto actual = service.create(certificateDto);
        assertEquals(certificateDto, actual);
    }

    @Test
    void createThrowsExceptionWithNullParam() {
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.create(null));
        assertEquals(40010, thrown.getErrorCode());
    }

    @Test
    void createThrowsExceptionWithInvalidName() {
        setInvalidName();
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.create(certificateDto));
        assertEquals(40030, thrown.getErrorCode());
    }

    @Test
    void createThrowsExceptionWithInvalidDescription() {
        setInvalidDescription();
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.create(certificateDto));
        assertEquals(40031, thrown.getErrorCode());
    }

    @Test
    void createThrowsExceptionWithInvalidPrice() {
        setInvalidPrice();
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.create(certificateDto));
        assertEquals(40032, thrown.getErrorCode());
    }

    @Test
    void createThrowsExceptionWithInvalidDuration() {
        setInvalidDuration();
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.create(certificateDto));
        assertEquals(40033, thrown.getErrorCode());
    }

    @Test
    void createThrowsExceptionWithInvalidTagList() {
        setInvalidTagList();
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.create(certificateDto));
        assertEquals(40020, thrown.getErrorCode());
    }

    @Test
    void updateWithValidParam() {
        setValidCertificateParams();
        doReturn(Optional.of(certificate)).when(certificateDao).findById(anyLong());
        doReturn(updatedTag).when(entityConverter).convertDtoIntoEntity(any(TagDto.class));
        doReturn(updatedTag).when(tagService).createTag(Mockito.any(Tag.class));
        doNothing().when(certificateDao).update(certificate);
        doReturn(updatedCertificateDto).when(dtoConverter).convertEntityIntoDto(any(GiftCertificate.class));
        GiftCertificateDto actual = service.update(1L, updatedCertificateDto);
        assertEquals(updatedCertificateDto, actual);
    }

    @Test
    void updateThrowsExceptionWithNullParam() {
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.update(1L, null));
        assertEquals(40010, thrown.getErrorCode());
    }

    @Test
    void updateThrowsExceptionWithInvalidName() {
        setInvalidName();
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.update(1L, updatedCertificateDto));
        assertEquals(40030, thrown.getErrorCode());
    }

    @Test
    void updateThrowsExceptionWithInvalidDescription() {
        setInvalidDescription();
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.update(1L, updatedCertificateDto));
        assertEquals(40031, thrown.getErrorCode());
    }

    @Test
    void updateThrowsExceptionWithInvalidPrice() {
        setInvalidPrice();
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.update(1L, updatedCertificateDto));
        assertEquals(40032, thrown.getErrorCode());
    }

    @Test
    void updateThrowsExceptionWithInvalidDuration() {
        setInvalidDuration();
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.update(1L, updatedCertificateDto));
        assertEquals(40033, thrown.getErrorCode());
    }

    @Test
    void updateThrowsExceptionWithInvalidTagList() {
        setInvalidTagList();
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.update(1L, updatedCertificateDto));
        assertEquals(40020, thrown.getErrorCode());
    }

    @Test
    void updateThrowsExceptionWithNonExistentEntity() {
        setValidCertificateParams();
        doReturn(Optional.empty()).when(certificateDao).findById(anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.update(1L, updatedCertificateDto));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void findByIdWithExistentEntity() {
        doReturn(certificateDto).when(dtoConverter).convertEntityIntoDto(any(GiftCertificate.class));
        doReturn(Optional.of(certificate)).when(certificateDao).findById(anyLong());
        GiftCertificateDto actual = service.findById(1L);
        assertEquals(certificateDto, actual);
    }

    @Test
    void findByIdThrowsExceptionWithNonExistentEntity() {
        doReturn(Optional.empty()).when(certificateDao).findById(anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findById(1L));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void findCertificateByIdWithExistentEntity() {
        doReturn(Optional.of(certificate)).when(certificateDao).findById(anyLong());
        GiftCertificate actual = service.findCertificateById(1L);
        assertEquals(certificate, actual);
    }

    @Test
    void findCertificateByIdThrowsExceptionWithNonExistentEntity() {
        doReturn(Optional.empty()).when(certificateDao).findById(anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findCertificateById(1L));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void findAll() {
        UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, () -> service.findAll(pageable));
        assertEquals("findAll method isn't implemented in GiftCertificateServiceImpl class", thrown.getMessage());
    }

    @Test
    void findByAttributesWithValidParams() {
        findEntityNumber();
        doReturn(true).when(validator).isPageExists(any(CustomPageable.class), anyLong());
        doReturn(List.of(certificate)).when(certificateDao).findByAttributes(any(GiftCertificateAttribute.class), anyInt(), anyInt());
        doReturn(certificateDto).when(dtoConverter).convertEntityIntoDto(any(GiftCertificate.class));
        CustomPage<GiftCertificateDto> actual = service.findByAttributes(GiftCertificateAttributeDto.builder().build(), pageable);
        assertEquals(page, actual);
    }

    @Test
    void findByAttributesThrowsExceptionWithInvalidAttributes() {
        doReturn(false).when(validator).isAttributeDtoValid(any(GiftCertificateAttributeDto.class));
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findByAttributes(GiftCertificateAttributeDto.builder().build(), pageable));
        assertEquals(40034, thrown.getErrorCode());
    }

    @Test
    void findByAttributesThrowsExceptionWithInvalidPageable() {
        doReturn(true).when(validator).isAttributeDtoValid(any(GiftCertificateAttributeDto.class));
        doReturn(false).when(validator).isPageDataValid(any(CustomPageable.class));
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findByAttributes(GiftCertificateAttributeDto.builder().build(), pageable));
        assertEquals(40050, thrown.getErrorCode());
    }

    @Test
    void findByAttributesThrowsExceptionWithNonExistentPage() {
        findEntityNumber();
        doReturn(false).when(validator).isPageExists(any(CustomPageable.class), anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findByAttributes(GiftCertificateAttributeDto.builder().build(), pageable));
        assertEquals(40051, thrown.getErrorCode());
    }

    @Test
    void deleteWithExistentEntity() {
        doReturn(Optional.of(certificate)).when(certificateDao).findById(anyLong());
        doReturn(false).when(certificateDao).isGiftCertificateUsedInOrders(anyLong());
        service.delete(1L);
        assertTrue(true);
    }

    @Test
    void deleteThrowsExceptionWithNonExistentEntity() {
        doReturn(Optional.empty()).when(certificateDao).findById(anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.delete(1L));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void deleteThrowsExceptionWithCertificateWhichUsedInOrders() {
        doReturn(Optional.of(certificate)).when(certificateDao).findById(anyLong());
        doReturn(true).when(certificateDao).isGiftCertificateUsedInOrders(anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.delete(1L));
        assertEquals(40910, thrown.getErrorCode());
    }

    private void setValidCertificateParams() {
        doReturn(true).when(validator).isNameValid(anyString(), any(ValidationType.class));
        doReturn(true).when(validator).isDescriptionValid(anyString(), any(ValidationType.class));
        doReturn(true).when(validator).isPriceValid(any(BigDecimal.class), any(ValidationType.class));
        doReturn(true).when(validator).isDurationValid(anyInt(), any(ValidationType.class));
        doReturn(true).when(validator).isTagListValid(anyList(), any(ValidationType.class));
    }

    private void setInvalidName() {
        doReturn(false).when(validator).isNameValid(anyString(), any(ValidationType.class));
    }

    private void setInvalidDescription() {
        doReturn(true).when(validator).isNameValid(anyString(), any(ValidationType.class));
        doReturn(false).when(validator).isDescriptionValid(anyString(), any(ValidationType.class));
    }

    private void setInvalidPrice() {
        doReturn(true).when(validator).isNameValid(anyString(), any(ValidationType.class));
        doReturn(true).when(validator).isDescriptionValid(anyString(), any(ValidationType.class));
        doReturn(false).when(validator).isPriceValid(any(BigDecimal.class), any(ValidationType.class));
    }

    private void setInvalidDuration() {
        doReturn(true).when(validator).isNameValid(anyString(), any(ValidationType.class));
        doReturn(true).when(validator).isDescriptionValid(anyString(), any(ValidationType.class));
        doReturn(true).when(validator).isPriceValid(any(BigDecimal.class), any(ValidationType.class));
        doReturn(false).when(validator).isDurationValid(anyInt(), any(ValidationType.class));
    }

    private void setInvalidTagList() {
        doReturn(true).when(validator).isNameValid(anyString(), any(ValidationType.class));
        doReturn(true).when(validator).isDescriptionValid(anyString(), any(ValidationType.class));
        doReturn(true).when(validator).isPriceValid(any(BigDecimal.class), any(ValidationType.class));
        doReturn(true).when(validator).isDurationValid(anyInt(), any(ValidationType.class));
        doReturn(false).when(validator).isTagListValid(anyList(), any(ValidationType.class));
    }

    private void findEntityNumber() {
        doReturn(true).when(validator).isAttributeDtoValid(any(GiftCertificateAttributeDto.class));
        doReturn(true).when(validator).isPageDataValid(any(CustomPageable.class));
        doReturn(GiftCertificateAttribute.builder().build()).when(entityConverter).convertDtoIntoEntity(any(GiftCertificateAttributeDto.class));
        doReturn(30L).when(certificateDao).findEntityNumber(any(GiftCertificateAttribute.class));
    }
}