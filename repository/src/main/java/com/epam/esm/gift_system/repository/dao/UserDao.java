package com.epam.esm.gift_system.repository.dao;

import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.repository.model.User;

import java.util.Optional;

public interface UserDao extends BaseDao<User> {
    Optional<User> findByName(String name);

    Optional<Order> findUserOrderById(Long userId, Long orderId);

    boolean isUserNameFree(String name);

    boolean isUserHasOrders(Long id);
}