package com.epam.esm.gift_system.repository.dao.impl;

import com.epam.esm.gift_system.repository.dao.StatisticsDao;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.repository.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class StatisticsDaoImpl implements StatisticsDao {
    @PersistenceContext
    private final EntityManager entityManager;
    private final CriteriaBuilder builder;

    public StatisticsDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.builder = entityManager.getCriteriaBuilder();
    }

    @Override
    public Tag findMostPopularTag() {
        CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
        Root<User> root = query.from(User.class);
        return null;                                                //todo
    }
}