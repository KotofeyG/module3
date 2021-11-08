package com.epam.esm.gift_system.repository.dao.constant;

public class SqlQuery {
    public static final String COUNT_TAG_USAGE = "SELECT count(*) FROM tags_certificates WHERE tag_id=?";
    public static final String COUNT_CERTIFICATE_USAGE = "SELECT count(*) FROM orders_certificates WHERE gift_certificate_id=?";
    public static final String FIND_MOST_POPULAR_TAG_OF_RICHEST_USER = """
            SELECT t.id, t.name FROM tags as t
            JOIN tags_certificates as tc ON tc.tag_id=t.id
            JOIN orders_certificates as oc ON oc.gift_certificate_id=tc.gift_certificate_id
            JOIN orders as o ON o.id=oc.order_id AND o.user_id=(
            	SELECT u.id FROM users as u
                JOIN orders as ord ON ord.user_id=u.id
            	GROUP BY u.id ORDER BY sum(ord.order_cost) DESC LIMIT 1
            ) GROUP BY t.id ORDER BY count(t.id) DESC LIMIT 1;""";

    private SqlQuery() {
    }
}