package com.epam.esm.gift_system.service.validator;

import com.epam.esm.gift_system.repository.model.EntityField;
import com.epam.esm.gift_system.service.dto.GiftCertificateAttributeDto;
import com.epam.esm.gift_system.service.dto.TagDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.epam.esm.gift_system.service.validator.EntityValidator.ValidationType.*;

@Component
public class EntityValidator {
    private static final int ZERO = 0;
    private static final int MIN_EXPIRATION_PERIOD = 1;
    private static final int MAX_EXPIRATION_PERIOD = 255;
    private static final Set<String> AVAILABLE_SORT_ORDERS = Set.of("asc", "desc");
    private static final String NAME_REGEX = "[A-Za-zА-Яа-я-, ]{2,75}";
    private static final String DESCRIPTION_REGEX = "[A-Za-zА-Яа-я\\d-.,:;!?()\" ]{2,255}";
    private static final String PRICE_REGEX = "\\d{1,5}|\\d{1,5}\\.\\d{1,2}";

    public enum ValidationType {
        INSERT, UPDATE
    }

    public boolean isNameValid(String name, ValidationType type) {
        return type == INSERT
                ? isNotNullAndBlank(name) && name.matches(NAME_REGEX)
                : Objects.isNull(name) || (!name.isBlank() && name.matches(NAME_REGEX));
    }

    public boolean isDescriptionValid(String description, ValidationType type) {
        return type == INSERT
                ? isNotNullAndBlank(description) && description.matches(DESCRIPTION_REGEX)
                : Objects.isNull(description) || (!description.isBlank() && description.matches(DESCRIPTION_REGEX));
    }

    private boolean isNotNullAndBlank(String field) {
        return Objects.nonNull(field) && !field.isBlank();
    }

    public boolean isPriceValid(BigDecimal price, ValidationType type) {
        return type == INSERT
                ? Objects.nonNull(price) && matchPriceToRegex(price)
                : Objects.isNull(price) || matchPriceToRegex(price);
    }

    private boolean matchPriceToRegex(BigDecimal price) {
        return String.valueOf(price.doubleValue()).matches(PRICE_REGEX);
    }

    public boolean isDurationValid(int duration, ValidationType type) {
        return type == INSERT
                ? duration >= MIN_EXPIRATION_PERIOD & duration <= MAX_EXPIRATION_PERIOD
                : duration == ZERO || (duration >= MIN_EXPIRATION_PERIOD & duration <= MAX_EXPIRATION_PERIOD);
    }

    public boolean isTagListValid(List<TagDto> tags, ValidationType type) {
        return type == INSERT
                ? !CollectionUtils.isEmpty(tags) && tags.stream()
                .map(tag -> Objects.nonNull(tag) && isNameValid(tag.getName(), INSERT))
                .filter(Boolean::booleanValue).count() == tags.size()
                : CollectionUtils.isEmpty(tags) || tags.stream()
                .map(tag -> Objects.nonNull(tag) && isNameValid(tag.getName(), UPDATE))
                .filter(Boolean::booleanValue).count() == tags.size();
    }

    public boolean isAttributeListValid(GiftCertificateAttributeDto attributeDto) {
        String tagName = attributeDto.getTagName();
        String searchPart = attributeDto.getSearchPart();
        String orderSort = attributeDto.getOrderSort();
        List<String> sortingFields = attributeDto.getSortingFields();

        return isNameValid(tagName, UPDATE) && isDescriptionValid(searchPart, UPDATE)
                && (Objects.isNull(sortingFields) || EntityField.getNameList().containsAll(sortingFields))
                && (Objects.isNull(orderSort) || AVAILABLE_SORT_ORDERS.contains(orderSort.toLowerCase()));
    }
}