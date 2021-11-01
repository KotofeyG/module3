package com.epam.esm.gift_system.repository.dao;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import com.epam.esm.gift_system.repository.model.Tag;
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
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.EMPTY;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.ID;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.NAME;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.TAG_LIST;

public class SqlQueryBuilder {
    private final CriteriaBuilder builder;

    public SqlQueryBuilder(CriteriaBuilder builder) {
        this.builder = builder;
    }

    public CriteriaQuery<GiftCertificate> buildCertificateQueryForSearchAndSort(GiftCertificateAttribute attribute) {
        String tagName = Objects.nonNull(attribute.getTagName()) ? attribute.getTagName() : EMPTY;
        String searchPart = Objects.nonNull(attribute.getSearchPart()) ? ANY_TEXT + attribute.getSearchPart() + ANY_TEXT : EMPTY;
        String orderSort = Objects.nonNull(attribute.getOrderSort()) ? attribute.getOrderSort() : DEFAULT_SORT;
        List<String> sortingFields = attribute.getSortingFields();

        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        query.select(root);

        Predicate tagNamePredicate = buildPredicateByTagName(root, tagName);
        Predicate searchPartPredicate = buildPredicateBySearchPart(root, searchPart);
        Predicate resultPredicate = builder.and(tagNamePredicate, searchPartPredicate);
        query.where(resultPredicate);

        List<Order> orderList = buildOrderListByFields(root, sortingFields, orderSort);
        query.orderBy(orderList);
        return query;
    }

    private Predicate buildPredicateByTagName(Root<GiftCertificate> root, String tagName) {
        Predicate result = builder.conjunction();
        if (!tagName.equals(EMPTY)) {
            Join<GiftCertificate, Tag> tagJoin = root.join(TAG_LIST);
            result = builder.equal(tagJoin.get(NAME), tagName);
        }
        return result;
    }

    private Predicate buildPredicateBySearchPart(Root<GiftCertificate> root, String searchPart) {
        Predicate result = builder.conjunction();
        if (!searchPart.equals(EMPTY)) {
            Predicate namePartPredicate = builder.like(root.get(NAME), searchPart);
            Predicate descriptionPartPredicate = builder.like(root.get(DESCRIPTION), searchPart);
            result = builder.or(namePartPredicate, descriptionPartPredicate);
        }
        return result;
    }

    private List<Order> buildOrderListByFields(Root<GiftCertificate> root, List<String> fieldList, String orderSort) {
        return CollectionUtils.isEmpty(fieldList)
                ? List.of(buildOrderByField(root, ID, orderSort))
                : fieldList.stream().map(field -> buildOrderByField(root, field, orderSort)).toList();
    }

    private Order buildOrderByField(Root<GiftCertificate> root, String fieldName, String orderSort) {
        return orderSort.equals(DEFAULT_SORT) ? builder.asc(root.get(fieldName)) : builder.desc(root.get(fieldName));
    }
}