package com.epam.esm.gift_system.repository.dao.impl;

import com.epam.esm.gift_system.repository.config.TestConfig;
import com.epam.esm.gift_system.repository.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestConfig.class})
@Transactional
@ActiveProfiles("test")
class TagDaoImplTest {
    private final TagDaoImpl tagDao;
    private Tag tag;

    @Autowired
    public TagDaoImplTest(TagDaoImpl tagDao) {
        this.tagDao = tagDao;
    }

    @BeforeEach
    void setUp() {
        tag = Tag.builder().name("NewTag").build();
    }

    @Test
    void create() {
        Tag actual = tagDao.create(tag);
        assertEquals("NewTag", actual.getName());
    }

    @Test
    void update() {
        tag.setId(5L);
        tag.setName("New");
        tagDao.update(tag);
        assertTrue(true);
    }

    @Test
    void findById() {
        Optional<Tag> actual = tagDao.findById(1L);
        assertTrue(actual.isPresent());
    }

    @Test
    void FindByIdReturnsEmptyWithNonExistentTag() {
        Optional<Tag> actual = tagDao.findById(100L);
        assertTrue(actual.isEmpty());
    }

    @Test
    void findByName() {
        Optional<Tag> actual = tagDao.findByName("HR");
        assertTrue(actual.isPresent());
    }

    @Test
    void findByNameReturnsEmptyWithNonExistentTag() {
        Optional<Tag> actual = tagDao.findByName("NonExistent");
        assertTrue(actual.isEmpty());
    }

    @Test
    void findAll() {
        List<Tag> tags = tagDao.findAll(0, 4);
        assertEquals(4, tags.size());
    }

    @Test
    void delete() {
        tag.setId(2L);
        tag.setName("HR");
        tagDao.delete(tag);
        assertTrue(true);
    }

    @Test
    void findEntityNumber() {
        long actual = tagDao.findEntityNumber();
        assertEquals(4, actual);
    }

    @Test
    void isTagUsedInCertificatesWithTrueCondition() {
        boolean condition = tagDao.isTagUsedInCertificates(3L);
        assertTrue(condition);
    }

    @Test
    void isTagUsedInCertificatesWithFalseCondition() {
        boolean condition = tagDao.isTagUsedInCertificates(4L);
        assertFalse(condition);
    }
}