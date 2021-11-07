package com.epam.esm.gift_system.repository.dao.impl;

import com.epam.esm.gift_system.repository.dao.StatisticsDao;
import com.epam.esm.gift_system.repository.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import static com.epam.esm.gift_system.repository.dao.constant.SqlQuery.FIND_MOST_POPULAR_TAG_OF_RICHEST_USER;

@Repository
public class StatisticsDaoImpl implements StatisticsDao {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public StatisticsDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Tag> findMostPopularTag() {
        Query query = entityManager.createNativeQuery(FIND_MOST_POPULAR_TAG_OF_RICHEST_USER, Tag.class);
        return query.getResultList();
    }
}