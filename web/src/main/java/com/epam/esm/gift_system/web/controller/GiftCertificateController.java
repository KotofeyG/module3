package com.epam.esm.gift_system.web.controller;

import com.epam.esm.gift_system.service.GiftCertificateService;
import com.epam.esm.gift_system.service.dto.GiftCertificateAttributeDto;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class GiftCertificateController {
    private final GiftCertificateService certificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto certificateDto) {
        return certificateService.create(certificateDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto update(@PathVariable Long id, @RequestBody GiftCertificateDto certificateDto) {
        return certificateService.update(id, certificateDto);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto findById(@PathVariable Long id) {
        return certificateService.findById(id);
    }

    @GetMapping
    public List<GiftCertificateDto> findByAttributes(GiftCertificateAttributeDto attribute) {
        return certificateService.findByAttributes(attribute);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        certificateService.delete(id);
    }
}