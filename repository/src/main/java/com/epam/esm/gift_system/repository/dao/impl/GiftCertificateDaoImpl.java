package com.epam.esm.gift_system.repository.dao.impl;

import com.epam.esm.gift_system.repository.dao.GiftCertificateDao;
import com.epam.esm.gift_system.repository.dao.SqlQueryBuilder;
import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    @PersistenceContext
    private final EntityManager entityManager;

    public GiftCertificateDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
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
        SqlQueryBuilder sqlQueryBuilder = new SqlQueryBuilder(entityManager.getCriteriaBuilder());
        TypedQuery<GiftCertificate> typedQuery = entityManager
                .createQuery(sqlQueryBuilder.buildCertificateQueryForSearchAndSort(attribute));
        return typedQuery.getResultList();
    }

    @Override
    public void delete(GiftCertificate certificate) {
        entityManager.remove(certificate);
    }
}