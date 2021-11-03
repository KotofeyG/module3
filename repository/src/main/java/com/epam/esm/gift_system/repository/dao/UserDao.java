package com.epam.esm.gift_system.repository.dao;

import com.epam.esm.gift_system.repository.model.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {
    List<User> findAllByNameList(List<String> nameList);

    boolean isUserNameFree(String name);

    boolean isUserHasOrders(Long id);
}