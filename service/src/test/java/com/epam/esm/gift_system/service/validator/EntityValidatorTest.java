package com.epam.esm.gift_system.service.validator;

import com.epam.esm.gift_system.service.dto.CustomPageable;
import com.epam.esm.gift_system.service.dto.GiftCertificateAttributeDto;
import com.epam.esm.gift_system.service.dto.RequestOrderDto;
import com.epam.esm.gift_system.service.dto.TagDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.gift_system.service.validator.EntityValidator.ValidationType.STRONG;
import static com.epam.esm.gift_system.service.validator.EntityValidator.ValidationType.SOFT;
import static org.junit.jupiter.api.Assertions.*;

class EntityValidatorTest {
    private static EntityValidator validator;
    private RequestOrderDto orderDto;
    private GiftCertificateAttributeDto attributeDto;
    private CustomPageable pageable;

    @BeforeAll
    static void init() {
        validator = new EntityValidator();
    }

    @BeforeEach
    void setUp() {
        orderDto = RequestOrderDto.builder().userId(1L).certificateIdList(List.of(1L,2L,3L,4L,5L)).build();
        attributeDto = GiftCertificateAttributeDto.builder()
                .tagNameList(List.of("NameOne", "NameTwo", "NameThree"))
                .searchPart("any")
                .sortingFieldList(List.of("name", "description", "price", "duration", "createDate", "lastUpdateDate"))
                .orderSort("desc")
                .build();
        pageable = new CustomPageable();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Name", "Tag-name", "another-tag-name", "Ra"})
    void isStrongNameValidReturnsTrueWithValidParams(String validName) {
        boolean condition = validator.isNameValid(validName, STRONG);
        assertTrue(condition);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"<Name>", "N", "Name!", "Name&", "'Name'", "<Name>"})
    void isStrongNameValidReturnsFalseWithInvalidParams(String invalidName) {
        boolean condition = validator.isNameValid(invalidName, STRONG);
        assertFalse(condition);
    }

    @Test
    void isStrongNameValidReturnsFalseWithMoreThen75SymbolsName() {
        String MoreThen75SymbolsName = "N".repeat(76);
        boolean condition = validator.isNameValid(MoreThen75SymbolsName, STRONG);
        assertFalse(condition);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"Name", "Tag-name", "another-tag-name", "Ra"})
    void isSoftNameValidReturnsTrueWithValidParams(String validName) {
        boolean condition = validator.isNameValid(validName, SOFT);
        assertTrue(condition);
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"<Name>", "N", "Name!", "Name&", "'Name'", "<Name>"})
    void isSoftNameValidReturnsFalseWithInvalidParams(String invalidName) {
        boolean condition = validator.isNameValid(invalidName, SOFT);
        assertFalse(condition);
    }

    @Test
    void isSoftNameValidReturnsFalseWithMoreThen75SymbolsName() {
        String MoreThen75SymbolsName = "N".repeat(76);
        boolean condition = validator.isNameValid(MoreThen75SymbolsName, SOFT);
        assertFalse(condition);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Description", "Description!", "Description?", "Description;", "Description.", "-Description-"})
    void isStrongDescriptionValidReturnsTrueWithValidDescription(String validDescription) {
        boolean condition = validator.isDescriptionValid(validDescription, STRONG);
        assertTrue(condition);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"<Description>", "Description%", "Description^", "Description&", "Description~", "Description@", "D"})
    void isStrongDescriptionValidReturnsFalseWithInvalidDescription(String invalidDescription) {
        boolean condition = validator.isDescriptionValid(invalidDescription, STRONG);
        assertFalse(condition);
    }

    @Test
    void isStrongDescriptionValidReturnsFalseWithMoreThen255SymbolsDescription() {
        String MoreThen255Description = "D".repeat(256);
        boolean condition = validator.isDescriptionValid(MoreThen255Description, STRONG);
        assertFalse(condition);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"Description", "Description!", "Description?", "Description;", "Description.", "-Description-"})
    void isSoftDescriptionValidReturnsTrueWithValidDescription(String validDescription) {
        boolean condition = validator.isDescriptionValid(validDescription, SOFT);
        assertTrue(condition);
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"<Description>", "Description%", "Description^", "Description&", "Description~", "Description@", "D"})
    void isSoftDescriptionValidReturnsFalseWithInvalidDescription(String invalidDescription) {
        boolean condition = validator.isDescriptionValid(invalidDescription, SOFT);
        assertFalse(condition);
    }

    @Test
    void isSoftDescriptionValidReturnsFalseWithMoreThen255SymbolsDescription() {
        String MoreThen255Description = "D".repeat(256);
        boolean condition = validator.isDescriptionValid(MoreThen255Description, SOFT);
        assertFalse(condition);
    }

    @ParameterizedTest
    @ValueSource(strings = {"25.25", "5", "0", "100", "10000", "0.2"})
    void isStrongPriceValidReturnsTrueWithValidPrice(String strPrice) {
        BigDecimal validPrice = new BigDecimal(strPrice);
        boolean condition = validator.isPriceValid(validPrice, STRONG);
        assertTrue(condition);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"-20", "555555.55", "55.555"})
    void isStrongPriceValidReturnsFalseWithValidPrice(String strPrice) {
        BigDecimal validPrice = strPrice != null ? new BigDecimal(strPrice) : null;
        boolean condition = validator.isPriceValid(validPrice, STRONG);
        assertFalse(condition);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"25.25", "5", "0", "100", "10000", "0.2"})
    void isSoftPriceValidReturnsTrueWithValidPrice(String strPrice) {
        BigDecimal validPrice = strPrice != null ? new BigDecimal(strPrice) : null;
        boolean condition = validator.isPriceValid(validPrice, SOFT);
        assertTrue(condition);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-20", "555555.55", "55.555"})
    void isSoftPriceValidReturnsFalseWithInvalidPrice(String strPrice) {
        BigDecimal invalidPrice = new BigDecimal(strPrice);
        boolean condition = validator.isPriceValid(invalidPrice, SOFT);
        assertFalse(condition);
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 200, 255})
    void isStrongDurationValidReturnsTrueWithValidDuration(int validDuration) {
        boolean condition = validator.isDurationValid(validDuration, STRONG);
        assertTrue(condition);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -10, 256})
    void isStrongDurationValidReturnsFalseWithInvalidDuration(int invalidDuration) {
        boolean condition = validator.isDurationValid(invalidDuration, STRONG);
        assertFalse(condition);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 10, 200, 255})
    void isSoftDurationValidReturnsTrueWithValidDuration(int validDuration) {
        boolean condition = validator.isDurationValid(validDuration, SOFT);
        assertTrue(condition);
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, 256})
    void isSoftDurationValidReturnsFalseWithInvalidDuration(int invalidDuration) {
        boolean condition = validator.isDurationValid(invalidDuration, SOFT);
        assertFalse(condition);
    }

    @Test
    void isStrongTagListValidWithValidTagList() {
        List<TagDto> validTagList = List.of(TagDto.builder().id(0L).name("NameOne").build()
                , TagDto.builder().id(0L).name("NameTwo").build()
                , TagDto.builder().id(0L).name("NameThree").build());
        boolean condition = validator.isTagListValid(validTagList, STRONG);
        assertTrue(condition);
    }

    @Test
    void isStrongTagNameListValidReturnsFalseWithEmptyTagList() {
        List<TagDto> emptyTagList = List.of();
        boolean condition = validator.isTagListValid(emptyTagList, STRONG);
        assertFalse(condition);
    }

    @Test
    void isStrongTagNameListValidReturnsFalseWithNullTagList() {
        List<TagDto> nullTagList = null;
        boolean condition = validator.isTagListValid(nullTagList, STRONG);
        assertFalse(condition);
    }

    @Test
    void isStrongTagNameListValidReturnsFalseWithNullTagInList() {
        List<TagDto> tagListWithNullTag = new ArrayList<>();
        tagListWithNullTag.add(TagDto.builder().id(0L).name("NameOne").build());
        tagListWithNullTag.add(null);
        boolean condition = validator.isTagListValid(tagListWithNullTag, STRONG);
        assertFalse(condition);
    }

    @Test
    void isStrongTagNameListValidReturnsFalseWithInvalidTagInList() {
        List<TagDto> tagListWithInvalidTag = List.of(TagDto.builder().id(0L).name("NameOne").build()
                , TagDto.builder().id(0L).name("<NameTwo>").build());
        boolean condition = validator.isTagListValid(tagListWithInvalidTag, STRONG);
        assertFalse(condition);
    }

    @Test
    void isSoftTagListValidWithValidTagList() {
        List<TagDto> validTagList = List.of(TagDto.builder().id(0L).name("NameOne").build()
                , TagDto.builder().id(0L).name("NameTwo").build()
                , TagDto.builder().id(0L).name("NameThree").build());
        boolean condition = validator.isTagListValid(validTagList, SOFT);
        assertTrue(condition);
    }

    @Test
    void isSoftTagNameListValidReturnsTrueWithEmptyTagList() {
        List<TagDto> emptyTagList = List.of();
        boolean condition = validator.isTagListValid(emptyTagList, SOFT);
        assertTrue(condition);
    }

    @Test
    void isSoftTagNameListValidReturnsTrueWithNullTagList() {
        List<TagDto> nullTagList = null;
        boolean condition = validator.isTagListValid(nullTagList, SOFT);
        assertTrue(condition);
    }

    @Test
    void isSoftTagNameListValidReturnsFalseWithNullTagInList() {
        List<TagDto> tagListWithNullTag = new ArrayList<>();
        tagListWithNullTag.add(TagDto.builder().id(0L).name("NameOne").build());
        tagListWithNullTag.add(null);
        boolean condition = validator.isTagListValid(tagListWithNullTag, SOFT);
        assertFalse(condition);
    }

    @Test
    void isSoftTagNameListValidReturnsFalseWithInvalidTagInList() {
        List<TagDto> tagListWithInvalidTag = List.of(TagDto.builder().id(0L).name("NameOne").build()
                , TagDto.builder().id(0L).name("<NameTwo>").build());
        boolean condition = validator.isTagListValid(tagListWithInvalidTag, SOFT);
        assertFalse(condition);
    }

    @Test
    void isAttributeDtoValid() {
        boolean condition = validator.isAttributeDtoValid(attributeDto);
        assertTrue(condition);
    }

    @Test
    void isAttributeDtoValidReturnsTrueWithEmptyTagNameList() {
        attributeDto.setTagNameList(List.of());
        boolean condition = validator.isAttributeDtoValid(attributeDto);
        assertTrue(condition);
    }

    @Test
    void isAttributeDtoValidReturnsTrueWithNullTagNameList() {
        attributeDto.setTagNameList(null);
        boolean condition = validator.isAttributeDtoValid(attributeDto);
        assertTrue(condition);
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"<NameOne>", "NameTwo@", "!NameThree!", "?NameFour", "135"})
    void isAttributeDtoValidReturnsFalseWithInvalidTagNameList(String invalidTagName) {
        attributeDto.setTagNameList(List.of(invalidTagName));
        boolean condition = validator.isAttributeDtoValid(attributeDto);
        assertFalse(condition);
    }

    @Test
    void isAttributeDtoValidReturnsFalseWithNullTagName() {
        attributeDto.setTagNameList(new ArrayList<>());
        attributeDto.getTagNameList().add(null);
        boolean condition = validator.isAttributeDtoValid(attributeDto);
        assertFalse(condition);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"SearchPart", "SearchPart!", "SearchPart?", "SearchPart;", "SearchPart.", "-SearchPart-"})
    void isAttributeDtoValidReturnsTrueWithValidSearchPart(String validSearchPart) {
        attributeDto.setSearchPart(validSearchPart);
        boolean condition = validator.isAttributeDtoValid(attributeDto);
        assertTrue(condition);
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"<PartOne>", "@PartTwo@", "%PartThree%", "$PartFour$", "`PartFive`", "#PartSix#", "^PartSeven^"})
    void isAttributeDtoValidReturnsFalseWithInvalidSearchPart(String invalidSearchPart) {
        attributeDto.setSearchPart(invalidSearchPart);
        boolean condition = validator.isAttributeDtoValid(attributeDto);
        assertFalse(condition);
    }

    @Test
    void isAttributeDtoValidReturnsTrueWithValidSortingFieldList() {
        attributeDto.setSortingFieldList(List.of("id", "name", "description", "price", "duration", "createDate", "lastUpdateDate"));
        boolean condition = validator.isAttributeDtoValid(attributeDto);
        assertTrue(condition);
    }

    @Test
    void isAttributeDtoValidReturnsTrueWithNullSortingFieldList() {
        attributeDto.setSortingFieldList(null);
        boolean condition = validator.isAttributeDtoValid(attributeDto);
        assertTrue(condition);
    }

    @ParameterizedTest
    @ValueSource(strings = {"<createDate>", "create", "createDate!", "?createDate", "create", "Date", "createDate1"})
    void isAttributeDtoValidReturnsFalseWithInvalidSortingFieldList(String invalidSortingField) {
        attributeDto.setSortingFieldList(List.of(invalidSortingField));
        boolean condition = validator.isAttributeDtoValid(attributeDto);
        assertFalse(condition);
    }

    @ParameterizedTest
    @ValueSource(strings = {"asc", "desc", "ASC", "DESC", "desC", "AsC"})
    @NullSource
    void isAttributeDtoValidReturnsTrueWithValidOrderSort(String validOrderSort) {
        attributeDto.setOrderSort(validOrderSort);
        boolean condition = validator.isAttributeDtoValid(attributeDto);
        assertTrue(condition);
    }

    @ParameterizedTest
    @ValueSource(strings = {"acs", "decs", "ACS", "DECS", "decS", "Acs", "asc!", "(desc)"})
    @EmptySource
    void isAttributeDtoValidReturnsFalseWithInvalidOrderSort(String invalidOrderSort) {
        attributeDto.setOrderSort(invalidOrderSort);
        boolean condition = validator.isAttributeDtoValid(attributeDto);
        assertFalse(condition);
    }

    @Test
    void isRequestOrderDataValidReturnsTrueWithValidParams() {
        boolean condition = validator.isRequestOrderDataValid(orderDto);
        assertTrue(condition);
    }

    @Test
    void isRequestOrderDataValidReturnsFalseWithInvalidUserId() {
        orderDto.setUserId(null);
        boolean condition = validator.isRequestOrderDataValid(orderDto);
        assertFalse(condition);
    }

    @Test
    void isRequestOrderDataValidReturnsFalseWithInvalidCertificateParam() {
        orderDto.setCertificateIdList(new ArrayList<>());
        orderDto.getCertificateIdList().add(null);
        boolean condition = validator.isRequestOrderDataValid(orderDto);
        assertFalse(condition);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1", "20, 20", "35, 35", "146, 146", "1014, 1014", "null, null"}, nullValues={"null"})
    void isPageDataValidReturnsTrueWithValidParams(Integer size, Integer page) {
        CustomPageable pageable = new CustomPageable();
        pageable.setSize(size);
        pageable.setPage(page);
        boolean condition = validator.isPageDataValid(pageable);
        assertTrue(condition);
    }

    @ParameterizedTest
    @CsvSource(value = {"-1, 35", "146, -1"})
    void isPageDataValidReturnsFalseWithInvalidParams(Integer size, Integer page) {
        pageable.setSize(size);
        pageable.setPage(page);
        boolean condition = validator.isPageDataValid(pageable);
        assertFalse(condition);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1, 20", "20, 20, 400", "35, 35, 1225", "null, null, 20"}, nullValues={"null"})
    void isPageExistsReturnsTrueWithRightPage(Integer size, Integer page, Long totalNumber) {
        pageable.setSize(size);
        pageable.setPage(page);
        boolean condition = validator.isPageExists(pageable, totalNumber);
        assertTrue(condition);
    }

    @ParameterizedTest
    @CsvSource({"20, 20, 380", "35, 35, 1100"})
    void isPageExistsReturnsFalseWithWrongPage(Integer size, Integer page, Long totalNumber) {
        pageable.setSize(size);
        pageable.setPage(page);
        boolean condition = validator.isPageExists(pageable, totalNumber);
        assertFalse(condition);
    }
}