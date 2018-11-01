package com.comic.backend.constant;

public enum EmailConfigKey {
    SMTP_HOST("smtp.host"), SMTP_USER("smtp.user"), SMTP_PASSWORD("smtp.password"), SMTP_PORT("smtp.port"),
    SMTP_FROM_ADDRESS("smtp.from.address"),
    SMTP_FROM_NAME("smtp.from.name"),
    SMTP_SUBJECT_EMAIL_VALIDATE("smtp.subject.email_validate"),
    SMTP_SUBJECT_RESET_PASSWORD("smtp.subject.reset_password"),
    SMTP_CONTENT_EMAIL_VALIDATE("smtp.content.email_validate"),
    SMTP_CONTENT_RESET_PASSWORD("smtp.content.reset_password"),
    SMTP_CONTENT_TYPE("smtp.content.type"),
    SMTP_TRANSPORT_STRATEGY("smtp.transport.strategy");

    private String name;

    public String getName() {
        return this.name;
    }

    private EmailConfigKey(String name) {
        this.name = name;
    }
}
