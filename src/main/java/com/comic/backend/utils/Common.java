package com.comic.backend.utils;

import com.comic.backend.configuration.ConfigurationEntity;
import com.comic.backend.configuration.ConfigurationRepository;
import com.comic.backend.constant.EmailConfigKey;
import com.comic.backend.constant.EmailSendType;
import com.comic.backend.user.UsersController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.email.EmailPopulatingBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Common {

    private static Logger logger = LogManager.getLogger(UsersController.class);

    @Autowired
    private ConfigurationRepository configurationRepository;


    private static Map<String, String> configMap = null;

    public Map<String, String> getConfigMap() {
        if (configMap == null) {
            configMap = new HashMap<>();
            List<ConfigurationEntity> configurationEntities = configurationRepository.findAll();
            for (ConfigurationEntity configurationEntity : configurationEntities) {
                configMap.put(configurationEntity.getKeyName(), configurationEntity.getKeyValue());
            }
        }
        return configMap;
    }


    public static boolean sendMail(EmailSendType emailSendType, List<EmailTo> tos, String content) {

        try {
            Common common = new Common();

            EmailPopulatingBuilder emailPopulatingBuilder =
                    EmailBuilder.startingBlank()
                            .from(common.getConfigMap().get(EmailConfigKey.SMTP_FROM_NAME.getName()), common.getConfigMap().get(EmailConfigKey.SMTP_FROM_ADDRESS.getName()));

            for (EmailTo emailTo : tos) {
                emailPopulatingBuilder = emailPopulatingBuilder.to(emailTo.getName(), emailTo.getAddress());
            }

            String subject = "";
            String contentText = "";

            if (EmailSendType.VALIDATE_EMAIL_ADDRESS.equals(emailSendType)) {
                subject = common.getConfigMap().get(EmailConfigKey.SMTP_SUBJECT_EMAIL_VALIDATE.getName());
                contentText = common.getConfigMap().get(EmailConfigKey.SMTP_CONTENT_EMAIL_VALIDATE.getName()).replaceAll("@@link@@", content);
            } else if (EmailSendType.RESET_PASSWORD.equals(emailSendType)) {
                subject = common.getConfigMap().get(EmailConfigKey.SMTP_SUBJECT_RESET_PASSWORD.getName());
                contentText = common.getConfigMap().get(EmailConfigKey.SMTP_CONTENT_RESET_PASSWORD.getName()).replaceAll("@@password@@", content);
            }

            if ("HTML".equalsIgnoreCase(common.getConfigMap().get(EmailConfigKey.SMTP_CONTENT_TYPE.getName()))) {
                emailPopulatingBuilder = emailPopulatingBuilder.withSubject(subject).withHTMLText(contentText);
            } else {
                emailPopulatingBuilder = emailPopulatingBuilder.withSubject(subject).withPlainText(contentText);
            }

            Email email = emailPopulatingBuilder.buildEmail();

            MailerBuilder.MailerRegularBuilder mailerRegularBuilder = MailerBuilder
                    .withSMTPServer(common.getConfigMap().get(EmailConfigKey.SMTP_HOST.getName()), Integer.parseInt(common.getConfigMap().get(EmailConfigKey.SMTP_PORT.getName())), common.getConfigMap().get(EmailConfigKey.SMTP_USER.getName()), common.getConfigMap().get(EmailConfigKey.SMTP_PASSWORD.getName()))
                    .withTransportStrategy(common.getTransportStrategy());
            mailerRegularBuilder.buildMailer().sendMail(email);
            return true;
        } catch (Exception e) {
            logger.error("Has error while send email. ", e);
            return false;
        }


    }

    public TransportStrategy getTransportStrategy() {
        String name = getConfigMap().get(EmailConfigKey.SMTP_TRANSPORT_STRATEGY.getName());
        TransportStrategy transportStrategy = TransportStrategy.SMTP;
        if ("SMTP_TLS".equalsIgnoreCase(name)) {
            transportStrategy = TransportStrategy.SMTP_TLS;
        } else if ("SMTPS".equalsIgnoreCase(name)) {
            transportStrategy = TransportStrategy.SMTPS;
        }
        return transportStrategy;
    }


}
