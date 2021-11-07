package com.epam.esm.gift_system.repository.dao.impl;

import com.epam.esm.gift_system.repository.config.TestConfig;
import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import com.epam.esm.gift_system.repository.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestConfig.class})
@Transactional
@ActiveProfiles("test")
class GiftCertificateDaoImplTest {
    private final GiftCertificateDaoImpl certificateDao;
    private Tag tag;
    private GiftCertificate certificate;
    private GiftCertificate deletedCertificate;
    private GiftCertificateAttribute attribute;

    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDaoImpl certificateDao) {
        this.certificateDao = certificateDao;
    }

    @BeforeEach
    void setUp() {
        tag = Tag.builder().id(1L).name("NewTagName").build();
        certificate = GiftCertificate.builder()
                .name("NewCertificate")
                .description("Description")
                .price(new BigDecimal("10"))
                .duration(5)
                .createDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .lastUpdateDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .tagList(Set.of(tag))
                .build();
        deletedCertificate = GiftCertificate.builder()
                .id(1L)
                .name("certificate 1")
                .description("description 1")
                .price(new BigDecimal("1.1"))
                .createDate(LocalDateTime.of(2021, 10, 8, 11, 11, 11))
                .lastUpdateDate(LocalDateTime.of(2021, 1, 1, 1, 22, 11))
                .build();
        attribute = GiftCertificateAttribute.builder().searchPart("certificate").build();
    }

    @Test
    void create() {
        certificateDao.create(certificate);
        assertNotNull(certificate.getId());
    }

    @Test
    void update() {
        certificate.setId(3L);
        certificateDao.update(certificate);
        assertTrue(true);
    }

    @Test
    void findByIdWithExistentEntity() {
        Optional<GiftCertificate> optional = certificateDao.findById(1L);
        assertTrue(optional.isPresent());
    }

    @Test
    void findByIdWithNonExistentEntity() {
        Optional<GiftCertificate> optional = certificateDao.findById(100L);
        assertTrue(optional.isEmpty());
    }

    @Test
    void findAll() {
        List<GiftCertificate> actual = certificateDao.findAll(0, 3);
        assertEquals(3, actual.size());
    }

    @Test
    void findByAttributes() {
        List<GiftCertificate> actual = certificateDao.findByAttributes(attribute, 0, 3);
        assertEquals(3, actual.size());
    }

    @Test
    void delete() {
        certificateDao.delete(deletedCertificate);
        assertTrue(true);
    }

    @Test
    void findEntityNumber() {
        long actual = certificateDao.findEntityNumber();
        assertEquals(3L, actual);
    }

    @Test
    void findEntityNumberByAttribute() {
        long actual = certificateDao.findEntityNumber(attribute);
        assertEquals(3L, actual);
    }
}