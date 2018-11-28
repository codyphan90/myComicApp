package com.comic.backend.constant;

public enum Permission {
    READ_ONLY(1), READ_COPY(2);

    private int value;

    public int getValue() {
        return value;
    }

    private Permission(int value) {
        this.value = value;
    }
}
