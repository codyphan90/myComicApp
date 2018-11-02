package com.comic.backend.constant;

public enum ConfigKey {
    SMTP_HOST("smtp.host"), SMTP_USER("smtp.user"), SMTP_PASSWORD("smtp.password"), SMTP_PORT("smtp.port"),
    SMTP_FROM_ADDRESS("smtp.from.address"),
    SMTP_FROM_NAME("smtp.from.name"),
    SMTP_SUBJECT_EMAIL_VALIDATE("smtp.subject.email_validate"),
    SMTP_SUBJECT_RESET_PASSWORD("smtp.subject.reset_password"),
    SMTP_CONTENT_EMAIL_VALIDATE("smtp.content.email_validate"),
    SMTP_CONTENT_RESET_PASSWORD("smtp.content.reset_password"),
    SMTP_CONTENT_TYPE("smtp.content.type"),
    SMTP_TRANSPORT_STRATEGY("smtp.transport.strategy"),
    WEB_HOME_PAGE("server.web.homepage"),
    SERVER_API_ACTIVE_USER("server.api.active_user"),
    TIME_OUT_MINUTES("server.session_time_out"),
    EXPIRE_MINUTES("server.session_expire"),
    WEB_VALIDATE_EMAIL("server.web.validate_email"),
    SECRET_LOGIN_KEY("server.secret.login_key"),
    SECRET_ACTIVE_KEY("server.secret.active_key");

    private String name;

    public String getName() {
        return this.name;
    }

    private ConfigKey(String name) {
        this.name = name;
    }
}
