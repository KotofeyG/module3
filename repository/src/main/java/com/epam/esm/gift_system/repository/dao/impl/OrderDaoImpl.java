package com.epam.esm.gift_system.repository.dao.impl;

import com.epam.esm.gift_system.repository.dao.OrderDao;
import com.epam.esm.gift_system.repository.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.ID;

@Repository
public class OrderDaoImpl implements OrderDao {
    @PersistenceContext
    private final EntityManager entityManager;
    private final CriteriaBuilder builder;

    public OrderDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.builder = entityManager.getCriteriaBuilder();
    }

    @Override
    public Order create(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public void update(Order order) {
        entityManager.merge(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public List<Order> findAll() {
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);
        query.orderBy(builder.asc(root.get(ID)));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void delete(Order entity) {
        entityManager.remove(entity);
    }
}