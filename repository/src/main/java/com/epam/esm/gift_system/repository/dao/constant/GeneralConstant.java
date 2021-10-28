package com.epam.esm.gift_system.repository.dao.constant;

import javax.persistence.criteria.Predicate;

public class GeneralConstant {
    public static final String ANY_TEXT = "%";
    public static final String DEFAULT_SORT = "asc";
    public static final String EMPTY = null;
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String TAGS = "tags";
    public static final Predicate EMPTY_PREDICATE = null;
    public static final int ZERO_ROWS_NUMBER = 0;
    public static final int FIRST_PARAM_INDEX = 1;

    private GeneralConstant() {
    }
}