package com.mutsa.mutsamarket.entity.enumtype;

public enum ItemStatus {

    SALE("판매중"),
    SOLD_OUT("판매완료");

    private final String value;

    ItemStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
