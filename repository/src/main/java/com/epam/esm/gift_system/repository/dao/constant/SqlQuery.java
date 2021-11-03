package com.epam.esm.gift_system.repository.dao.constant;

public class SqlQuery {
    public static final String COUNT_TAG_USAGE = "SELECT count(*) FROM tags_certificates WHERE tag_id=?";
    public static final String COUNT_CERTIFICATE_USAGE = "SELECT count(*) FROM orders_certificates WHERE gift_certificate_id=?";
    public static final String FIND_MOST_POPULAR_TAG_OF_RICHEST_USER = "";              //todo

    private SqlQuery() {
    }
}