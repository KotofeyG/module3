package com.epam.esm.gift_system.repository.dao;

import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.repository.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<User> {
    Optional<User> findByName(String name);

    List<Order> findUserOrders(User user, Integer offset, Integer limit);

    boolean isUserNameFree(String name);

    boolean isUserHasOrders(User user);

    Long findUserOrdersNumber(User user);
}