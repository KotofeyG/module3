package com.epam.esm.gift_system.repository.dao.impl;

import com.epam.esm.gift_system.repository.dao.GiftCertificateDao;
import com.epam.esm.gift_system.repository.dao.SqlQueryBuilder;
import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.FIRST_PARAM_INDEX;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.ZERO_ROWS_NUMBER;
import static com.epam.esm.gift_system.repository.dao.constant.SqlQuery.COUNT_CERTIFICATE_USAGE;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    @PersistenceContext
    private final EntityManager entityManager;
    private final SqlQueryBuilder sqlQueryBuilder;

    public GiftCertificateDaoImpl(EntityManager entityManager, SqlQueryBuilder sqlQueryBuilder) {
        this.entityManager = entityManager;
        this.sqlQueryBuilder = sqlQueryBuilder;
        this.sqlQueryBuilder.setBuilder(entityManager.getCriteriaBuilder());
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
    public List<GiftCertificate> findAll() {
        throw new UnsupportedOperationException("findAll method isn't implemented in GiftCertificateDaoImpl class");
    }

    @Override
    public List<GiftCertificate> findByAttributes(GiftCertificateAttribute attribute) {
        TypedQuery<GiftCertificate> typedQuery = entityManager
                .createQuery(sqlQueryBuilder.buildCertificateQueryForSearchAndSort(attribute));
        return typedQuery.getResultList();
    }

    @Override
    public void delete(GiftCertificate certificate) {
        entityManager.remove(certificate);
    }

    @Override
    public boolean isGiftCertificateUsedInOrders(Long id) {
        Query query = entityManager.createNativeQuery(COUNT_CERTIFICATE_USAGE);
        query.setParameter(FIRST_PARAM_INDEX, id);
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.longValue() > ZERO_ROWS_NUMBER;
    }
}