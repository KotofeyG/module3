package com.epam.esm.gift_system.repository.dao.impl;

import com.epam.esm.gift_system.repository.dao.GiftCertificateDao;
import com.epam.esm.gift_system.repository.dao.SqlQueryBuilder;
import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    @PersistenceContext
    private EntityManager entityManager;

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
    public List<GiftCertificate> findByAttributes(GiftCertificateAttribute attribute) {
        TypedQuery<GiftCertificate> typedQuery = entityManager
                .createQuery(SqlQueryBuilder.buildCertificateQueryForSearchAndSort(attribute, entityManager));
        return typedQuery.getResultList();
    }

    @Override
    public void delete(GiftCertificate certificate) {
        entityManager.remove(certificate);
    }
}