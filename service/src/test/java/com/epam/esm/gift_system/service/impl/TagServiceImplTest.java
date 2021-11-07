package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.TagDao;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.service.DtoConverterService;
import com.epam.esm.gift_system.service.EntityConverterService;
import com.epam.esm.gift_system.service.dto.CustomPage;
import com.epam.esm.gift_system.service.dto.CustomPageable;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.service.exception.GiftSystemException;
import com.epam.esm.gift_system.service.validator.EntityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @InjectMocks
    private TagServiceImpl service;
    @Mock
    private TagDao tagDao;
    @Mock
    private EntityValidator validator;
    @Mock
    private DtoConverterService dtoConverter;
    @Mock
    private EntityConverterService entityConverter;

    private Tag tag;
    private TagDto tagDto;
    private CustomPageable pageable;
    private CustomPage<TagDto> tagPage;

    @BeforeEach
    void setUp() {
        tag = Tag.builder().id(1L).name("TagName").build();
        tagDto = TagDto.builder().id(1L).name("TagName").build();
        pageable = new CustomPageable();
        pageable.setPage(10);
        pageable.setSize(1);
        tagPage = new CustomPage<>(List.of(tagDto, tagDto), pageable, 30L);
    }

    @Test
    void createWithExistentEntity() {
        doReturn(tag).when(entityConverter).convertDtoIntoEntity(any(TagDto.class));
        doReturn(true).when(validator).isNameValid(anyString(), any(EntityValidator.ValidationType.class));
        doReturn(Optional.of(tag)).when(tagDao).findByName(anyString());
        doReturn(tagDto).when(dtoConverter).convertEntityIntoDto(any(Tag.class));
        TagDto actual = service.create(tagDto);
        assertEquals(tagDto, actual);
    }

    @Test
    void createWithNonExistentEntity() {
        doReturn(tag).when(entityConverter).convertDtoIntoEntity(any(TagDto.class));
        doReturn(true).when(validator).isNameValid(anyString(), any(EntityValidator.ValidationType.class));
        doReturn(Optional.empty()).when(tagDao).findByName(anyString());
        doReturn(tag).when(tagDao).create(any(Tag.class));
        doReturn(tagDto).when(dtoConverter).convertEntityIntoDto(any(Tag.class));
        TagDto actual = service.create(tagDto);
        assertEquals(tagDto, actual);
    }

    @Test
    void createThrowsExceptionWithInvalidTagName() {
        doReturn(tag).when(entityConverter).convertDtoIntoEntity(any(TagDto.class));
        doReturn(false).when(validator).isNameValid(anyString(), any(EntityValidator.ValidationType.class));
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.create(tagDto));
        assertEquals(40020, thrown.getErrorCode());
    }

    @Test
    void createTagWithExistentEntity() {
        doReturn(true).when(validator).isNameValid(anyString(), any(EntityValidator.ValidationType.class));
        doReturn(Optional.of(tag)).when(tagDao).findByName(anyString());
        Tag actual = service.createTag(tag);
        assertEquals(tag, actual);
    }

    @Test
    void createTagWithNonExistentEntity() {
        doReturn(true).when(validator).isNameValid(anyString(), any(EntityValidator.ValidationType.class));
        doReturn(Optional.empty()).when(tagDao).findByName(anyString());
        doReturn(tag).when(tagDao).create(any(Tag.class));
        Tag actual = service.createTag(tag);
        assertEquals(tag, actual);
    }

    @Test
    void createTagThrowsExceptionWithInvalidTagName() {
        doReturn(false).when(validator).isNameValid(anyString(), any(EntityValidator.ValidationType.class));
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.createTag(tag));
        assertEquals(40020, thrown.getErrorCode());
    }

    @Test
    void update() {
        UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, () -> service.update(1L, tagDto));
        assertEquals("update method isn't implemented in TagServiceImpl class", thrown.getMessage());
    }

    @Test
    void findByIdWithExistentEntity() {
        doReturn(tagDto).when(dtoConverter).convertEntityIntoDto(any(Tag.class));
        doReturn(Optional.of(tag)).when(tagDao).findById(anyLong());
        TagDto actual = service.findById(1L);
        assertEquals(tagDto, actual);
    }

    @Test
    void findByIdThrowsExceptionWithNonExistentEntity() {
        doReturn(Optional.empty()).when(tagDao).findById(anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findById(1L));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void findAllWithValidParam() {
        doReturn(true).when(validator).isPageDataValid(any(CustomPageable.class));
        doReturn(30L).when(tagDao).findEntityNumber();
        doReturn(true).when(validator).isPageExists(any(CustomPageable.class), anyLong());
        doReturn(List.of(tag, tag)).when(tagDao).findAll(anyInt(), anyInt());
        doReturn(tagDto).when(dtoConverter).convertEntityIntoDto(any(Tag.class));
        CustomPage<TagDto> actual = service.findAll(pageable);
        assertEquals(tagPage, actual);
    }

    @Test
    void findAllWithInvalidPageable() {
        doReturn(false).when(validator).isPageDataValid(any(CustomPageable.class));
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findAll(pageable));
        assertEquals(40050, thrown.getErrorCode());
    }

    @Test
    void findAllWithNonExistentPage() {
        doReturn(true).when(validator).isPageDataValid(any(CustomPageable.class));
        doReturn(30L).when(tagDao).findEntityNumber();
        doReturn(false).when(validator).isPageExists(any(CustomPageable.class), anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findAll(pageable));
        assertEquals(40051, thrown.getErrorCode());
    }

    @Test
    void deleteWithExistentEntity() {
        doReturn(Optional.of(tag)).when(tagDao).findById(anyLong());
        doReturn(false).when(tagDao).isTagUsedInCertificates(anyLong());
        doNothing().when(tagDao).delete(any(Tag.class));
        service.delete(1L);
        assertTrue(true);
    }

    @Test
    void deleteThrowsExceptionWithNonExistentEntity() {
        doReturn(Optional.empty()).when(tagDao).findById(anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.delete(1L));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void deleteThrowsExceptionWithTagWhichUsedInCertificate() {
        doReturn(Optional.of(tag)).when(tagDao).findById(anyLong());
        doReturn(true).when(tagDao).isTagUsedInCertificates(anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.delete(1L));
        assertEquals(40910, thrown.getErrorCode());
    }
}