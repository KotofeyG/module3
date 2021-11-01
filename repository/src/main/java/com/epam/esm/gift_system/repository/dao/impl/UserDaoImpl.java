package com.epam.esm.gift_system.repository.dao.impl;

import com.epam.esm.gift_system.repository.dao.UserDao;
import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.repository.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.ID;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.NAME;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.USER_ID;
import static com.epam.esm.gift_system.repository.dao.constant.GeneralConstant.ZERO_ROWS_NUMBER;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private final EntityManager entityManager;
    private final CriteriaBuilder builder;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.builder = entityManager.getCriteriaBuilder();
    }

    @Override
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> findByName(String name) {
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        query.where(builder.equal(root.get(NAME), name));
        return entityManager.createQuery(query).getResultList().stream().findAny();
    }

    @Override
    public List<User> findAll() {
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<Order> findUserOrderById(Long userId, Long orderId) {
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);
        Predicate predicate = builder.equal(root.get(USER_ID), userId);
        predicate = builder.and(predicate, builder.equal(root.get(ID), orderId));
        query.where(predicate);
        return entityManager.createQuery(query).getResultList().stream().findAny();
    }

    @Override
    public void delete(User entity) {
        entityManager.remove(entity);
    }

    @Override
    public boolean isUserNameFree(String name) {
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        query.select(builder.count(root.get(ID)));
        query.where(builder.equal(root.get(NAME), name));
        return entityManager.createQuery(query).getSingleResult() == ZERO_ROWS_NUMBER;
    }

    @Override
    public boolean isUserHasOrders(Long id) {
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        query.where(builder.equal(root.get(ID), id));
        return entityManager.createQuery(query).getSingleResult().getOrderList().size() > ZERO_ROWS_NUMBER;
    }
}