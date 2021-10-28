package com.epam.esm.gift_system.repository.dao.impl;

import com.epam.esm.gift_system.repository.dao.TagDao;
import com.epam.esm.gift_system.repository.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.*;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.FIRST_PARAM_INDEX;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.NAME;
import static com.epam.esm.gift_system.repository.dao.constant.SqlQuery.*;

@Repository
public class TagDaoImpl implements TagDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public List<Tag> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root);
        query.orderBy(builder.asc(root.get(ID)));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public boolean isNotExisting(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(builder.count(root));
        query.where(builder.equal(root.get(NAME), name));
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult() == ZERO_ROWS_NUMBER;
    }

    @Override
    public boolean isUsed(Long id) {
        Query query = entityManager.createNativeQuery(COUNT_TAG_USAGE);
        query.setParameter(FIRST_PARAM_INDEX, id);
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.longValue() > ZERO_ROWS_NUMBER;
    }

    @Override
    public Tag findOrCreateTag(Tag tag) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root);
        query.where(builder.equal(root.get(NAME), tag.getName()));
        TypedQuery<Tag> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList().stream().findAny().orElseGet(() -> create(tag));
    }
}