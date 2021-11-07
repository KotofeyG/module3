package com.epam.esm.gift_system.web.controller;

import com.epam.esm.gift_system.service.GiftCertificateService;
import com.epam.esm.gift_system.service.dto.CustomPage;
import com.epam.esm.gift_system.service.dto.CustomPageable;
import com.epam.esm.gift_system.service.dto.GiftCertificateAttributeDto;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import com.epam.esm.gift_system.web.hateaos.HateaosBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/certificates")
public class GiftCertificateController {
    private final GiftCertificateService certificateService;
    private final HateaosBuilder hateaosBuilder;

    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService, HateaosBuilder hateaosBuilder) {
        this.certificateService = certificateService;
        this.hateaosBuilder = hateaosBuilder;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto certificateDto) {
        GiftCertificateDto created = certificateService.create(certificateDto);
        hateaosBuilder.setLinks(created);
        return created;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto update(@PathVariable Long id, @RequestBody GiftCertificateDto certificateDto) {
        GiftCertificateDto updated = certificateService.update(id, certificateDto);
        hateaosBuilder.setLinks(updated);
        return updated;
    }

    @GetMapping("/{id}")
    public GiftCertificateDto findById(@PathVariable Long id) {
        GiftCertificateDto certificateDto = certificateService.findById(id);
        hateaosBuilder.setLinks(certificateDto);
        return certificateDto;
    }

    @GetMapping
    public CustomPage<GiftCertificateDto> findByAttributes(GiftCertificateAttributeDto attribute, CustomPageable pageable) {
        CustomPage<GiftCertificateDto> page = certificateService.findByAttributes(attribute, pageable);
        page.getContent().forEach(hateaosBuilder::setLinks);
        return page;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        certificateService.delete(id);
    }
}