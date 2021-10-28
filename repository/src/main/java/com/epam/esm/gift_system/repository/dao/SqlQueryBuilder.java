package com.epam.esm.gift_system.repository.dao;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import com.epam.esm.gift_system.repository.model.Tag;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.*;

import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.*;

public class SqlQueryBuilder {


    public static CriteriaQuery<GiftCertificate> buildCertificateQueryForSearchAndSort(GiftCertificateAttribute attribute
            , EntityManager entityManager) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        query.select(root);

        String tagName = attribute.getTagName();
        String searchPart = Objects.nonNull(attribute.getSearchPart()) ? ANY_TEXT + attribute.getSearchPart() + ANY_TEXT : EMPTY;
        String orderSort = Objects.nonNull(attribute.getOrderSort()) ? attribute.getOrderSort() : DEFAULT_SORT;
        List<String> sortingFields = attribute.getSortingFields();

        Predicate resultPredicate = EMPTY_PREDICATE;

        if (Objects.nonNull(tagName)) {
            Join<GiftCertificate, Tag> tagJoin = root.join(TAGS);
            resultPredicate = builder.equal(tagJoin.get(NAME), tagName);
        }
        if (Objects.nonNull(searchPart)) {
            Predicate namePartPredicate = builder.like(root.get(NAME), searchPart);
            Predicate descriptionPartPredicate = builder.like(root.get(DESCRIPTION), searchPart);
            Predicate searchPartPredicate = builder.or(namePartPredicate, descriptionPartPredicate);
            resultPredicate = Objects.nonNull(resultPredicate)
                    ? builder.and(resultPredicate, searchPartPredicate)
                    : searchPartPredicate;
        }
        if (Objects.nonNull(resultPredicate)) {
            query.where(resultPredicate);
        }
        if (!CollectionUtils.isEmpty(sortingFields)) {
            List<Order> orders = new ArrayList<>();
            for (String field : sortingFields) {
                Order order = orderSort.equals(DEFAULT_SORT)
                        ? builder.asc(root.get(field))
                        : builder.desc(root.get(field));
                orders.add(order);
            }
            query.orderBy(orders);
        } else {
            if (orderSort.equals(DEFAULT_SORT)) {
                query.orderBy(builder.asc(root.get(ID)));
            } else {
                query.orderBy(builder.desc(root.get(ID)));
            }
        }
        return query;
    }
}