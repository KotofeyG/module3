package com.epam.esm.gift_system.repository.dao.impl;

import com.epam.esm.gift_system.repository.dao.GiftCertificateDao;
import com.epam.esm.gift_system.repository.dao.SqlQueryBuilder;
import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import org.springframework.beans.factory.annotation.Autowired;
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

import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.FIRST_PARAM_INDEX;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.ID;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.ZERO_ROWS_NUMBER;
import static com.epam.esm.gift_system.repository.dao.constant.SqlQuery.COUNT_CERTIFICATE_USAGE;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    @PersistenceContext
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final SqlQueryBuilder sqlQueryBuilder;

    @Autowired
    public GiftCertificateDaoImpl(EntityManager entityManager, SqlQueryBuilder sqlQueryBuilder) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.sqlQueryBuilder = sqlQueryBuilder;
        this.sqlQueryBuilder.setBuilder(criteriaBuilder);
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        entityManager.persist(certificate);
        return certificate;
    }

    @Override
    public void update(GiftCertificate certificate) {
        entityManager.merge(certificate);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public List<GiftCertificate> findAll(Integer offset, Integer limit) {
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        query.select(root);
        query.orderBy(criteriaBuilder.asc(root.get(ID)));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public List<GiftCertificate> findByAttributes(GiftCertificateAttribute attribute, Integer offset, Integer limit) {
        TypedQuery<GiftCertificate> typedQuery = entityManager
                .createQuery(sqlQueryBuilder.buildCertificateQueryForSearchAndSort(attribute));
        return typedQuery.setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public void delete(GiftCertificate certificate) {
        entityManager.remove(certificate);
    }

    @Override
    public Long findEntityNumber() {
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        query.select(criteriaBuilder.count(root));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public Long findEntityNumber(GiftCertificateAttribute attribute) {
        TypedQuery<Long> typedQuery = entityManager
                .createQuery(sqlQueryBuilder.buildQueryForCountCertificates(attribute));
        return typedQuery.getSingleResult();
    }

    @Override
    public boolean isGiftCertificateUsedInOrders(Long id) {
        Query query = entityManager.createNativeQuery(COUNT_CERTIFICATE_USAGE);
        query.setParameter(FIRST_PARAM_INDEX, id);
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.longValue() > ZERO_ROWS_NUMBER;
    }
}