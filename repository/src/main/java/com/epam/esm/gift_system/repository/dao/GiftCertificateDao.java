package com.epam.esm.gift_system.repository.dao;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;

import java.util.List;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    void update(GiftCertificate certificate);

    List<GiftCertificate> findByAttributes(GiftCertificateAttribute attribute);
}