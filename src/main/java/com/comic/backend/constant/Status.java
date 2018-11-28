package com.comic.backend.constant;

public enum Status {
    ACTIVE(1), IS_DELETED(-1);

    private int value;

    public int getValue() {
        return value;
    }

    private Status(int value) {
        this.value = value;
    }
}
