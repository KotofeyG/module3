package com.epam.esm.gift_system.repository.dao;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import com.epam.esm.gift_system.repository.model.Tag;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.ANY_TEXT;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.DEFAULT_SORT;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.DESCRIPTION;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.EMPTY_STRING;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.ID;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.NAME;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.TAG_LIST;

@Component
public class SqlQueryBuilder {
    private CriteriaBuilder builder;

    public void setBuilder(CriteriaBuilder builder) {
        this.builder = builder;
    }

    public CriteriaQuery<GiftCertificate> buildCertificateQueryForSearchAndSort(GiftCertificateAttribute attribute) {
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        query.select(root);
        Predicate resultPredicate = buildQueryCondition(root, attribute);
        query.where(resultPredicate);
        List<Order> orderList = buildOrderListByFields(root, attribute);
        query.orderBy(orderList);
        return query;
    }

    public CriteriaQuery<Long> buildQueryForCountCertificates(GiftCertificateAttribute attribute) {
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        query.select(builder.count(root));
        Predicate resultPredicate = buildQueryCondition(root, attribute);
        query.where(resultPredicate);
        return query;
    }

    private Predicate buildQueryCondition(Root<GiftCertificate> root, GiftCertificateAttribute attribute) {
        Predicate tagNamePredicate = buildByTagName(root, attribute);
        Predicate searchPartPredicate = buildBySearchPart(root, attribute);
        return builder.and(tagNamePredicate, searchPartPredicate);
    }

    private Predicate buildByTagName(Root<GiftCertificate> root, GiftCertificateAttribute attribute) {
        List<String> tagNameList = attribute.getTagNameList();
        return CollectionUtils.isEmpty(tagNameList)
                ? builder.conjunction()
                : tagNameList.stream().map(tagName -> {
            Join<GiftCertificate, Tag> tagJoin = root.join(TAG_LIST);
            return builder.equal(tagJoin.get(NAME), tagName);
        }).reduce(builder.conjunction(), builder::and);
    }

    private Predicate buildBySearchPart(Root<GiftCertificate> root, GiftCertificateAttribute attribute) {
        String searchPart = Objects.nonNull(attribute.getSearchPart()) ? ANY_TEXT + attribute.getSearchPart() + ANY_TEXT : EMPTY_STRING;
        Predicate result = builder.conjunction();
        if (!EMPTY_STRING.equals(searchPart)) {
            Predicate namePartPredicate = builder.like(root.get(NAME), searchPart);
            Predicate descriptionPartPredicate = builder.like(root.get(DESCRIPTION), searchPart);
            result = builder.or(namePartPredicate, descriptionPartPredicate);
        }
        return result;
    }

    private List<Order> buildOrderListByFields(Root<GiftCertificate> root, GiftCertificateAttribute attribute) {
        String orderSort = Objects.nonNull(attribute.getOrderSort()) ? attribute.getOrderSort() : DEFAULT_SORT;
        List<String> fieldList = attribute.getSortingFieldList();
        return CollectionUtils.isEmpty(fieldList)
                ? List.of(buildOrderByField(root, ID, orderSort))
                : fieldList.stream().map(field -> buildOrderByField(root, field, orderSort)).toList();
    }

    private Order buildOrderByField(Root<GiftCertificate> root, String fieldName, String orderSort) {
        return orderSort.equals(DEFAULT_SORT) ? builder.asc(root.get(fieldName)) : builder.desc(root.get(fieldName));
    }
}