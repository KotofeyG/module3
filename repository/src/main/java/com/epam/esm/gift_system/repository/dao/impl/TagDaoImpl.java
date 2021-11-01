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

import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.ID;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.NAME;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.ZERO_ROWS_NUMBER;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.FIRST_PARAM_INDEX;
import static com.epam.esm.gift_system.repository.dao.constant.SqlQuery.COUNT_TAG_USAGE;

@Repository
public class TagDaoImpl implements TagDao {
    @PersistenceContext
    private final EntityManager entityManager;
    private final CriteriaBuilder builder;

    public TagDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.builder = entityManager.getCriteriaBuilder();
    }

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public void update(Tag tag) {
        entityManager.merge(tag);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Tag findOrCreateTag(Tag tag) {
        CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root);
        query.where(builder.equal(root.get(NAME), tag.getName()));
        TypedQuery<Tag> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList().stream().findAny().orElseGet(() -> create(tag));
    }

    @Override
    public List<Tag> findAll() {
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
    public Optional<Tag> findByName(String name) {
        CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root);
        query.where(builder.equal(root.get(NAME), name));
        return entityManager.createQuery(query).getResultList().stream().findAny();
    }

    @Override
    public boolean isTagUsedInCertificates(Long id) {
        Query query = entityManager.createNativeQuery(COUNT_TAG_USAGE);
        query.setParameter(FIRST_PARAM_INDEX, id);
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.longValue() > ZERO_ROWS_NUMBER;
    }
}