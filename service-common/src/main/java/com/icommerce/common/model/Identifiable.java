package com.icommerce.common.model;

public interface Identifiable<T> {

    long getId();

    void setId(long id);

    boolean deepEquals(T other);

    int deepHashCode();

}
