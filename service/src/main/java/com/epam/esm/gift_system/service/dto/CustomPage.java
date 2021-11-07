package com.epam.esm.gift_system.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.Collection;

import static com.epam.esm.gift_system.service.constant.GeneralConstant.ZERO;

@Data
@JsonPropertyOrder({"content", "size", "total-elements", "total-pages", "number"})
public class CustomPage<T> {
    @JsonProperty("content")
    private Collection<T> content;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("total-elements")
    private Long totalElements;
    @JsonProperty("total-pages")
    private Long totalPages;
    @JsonProperty("number")
    private Integer number;

    public CustomPage(Collection<T> content, CustomPageable pageable, Long totalElements) {
        this.content = content;
        this.size = pageable.getSize();
        this.totalElements = totalElements;
        this.totalPages = totalElements != ZERO ? (long) Math.ceil((double) totalElements / pageable.getSize()) : ZERO;
        this.number = totalElements != ZERO ? pageable.getPage() + 1 : ZERO;
    }
}