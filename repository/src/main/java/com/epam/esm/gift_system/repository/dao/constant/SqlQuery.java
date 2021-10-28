package com.epam.esm.gift_system.repository.dao.constant;

public class SqlQuery {
    public static final String COUNT_TAG_USAGE = "SELECT count(*) FROM tags_certificates WHERE tag_id=?";

    private SqlQuery() {
    }
}