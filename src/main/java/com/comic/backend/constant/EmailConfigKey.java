package com.comic.backend.constant;

public enum EmailConfigKey {

    SMTP_HOST("smtp.host"), SMTP_USER("smtp.user"), SMTP_PASSWORD("smtp.password"), SMTP_PORT("smtp.host");

    private String name;

    public String getName() {
        return this.name;
    }

    private EmailConfigKey(String name) {
        this.name = name;
    }
}
