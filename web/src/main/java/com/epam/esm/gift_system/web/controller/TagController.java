package com.epam.esm.gift_system.web.controller;

import com.epam.esm.gift_system.service.TagService;
import com.epam.esm.gift_system.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody TagDto tagDto) {
        return tagService.create(tagDto);
    }

    @GetMapping("/{id}")
    public TagDto findById(@PathVariable Long id) {
        return tagService.findById(id);
    }

    @GetMapping
    public List<TagDto> findAll() {
        return tagService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }
}