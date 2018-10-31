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

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Common {

    private static Logger logger = LogManager.getLogger(UsersController.class);

    @Autowired
    private ConfigurationRepository configurationRepository;


    private static Map<String, String> configMap = null;

    public void loadConfig() {
        configMap = new HashMap<>();
        List<ConfigurationEntity> configurationEntities = configurationRepository.findAll();
        for (ConfigurationEntity configurationEntity : configurationEntities) {
            configMap.put(configurationEntity.getKeyName(), configurationEntity.getKeyValue());
        }
    }

    public static String getValueByName(String name) {
        if (configMap == null) {
            new Common().loadConfig();
        }
        return configMap != null ? configMap.get(name) : null;
    }


    public static boolean sendMail(EmailSendType emailSendType, List<EmailTo> tos, String content) {

        try {
            if (configMap == null) {
                Common common = new Common();
                common.loadConfig();
            }

            EmailPopulatingBuilder emailPopulatingBuilder =
                    EmailBuilder.startingBlank()
                            .from(getValueByName(EmailConfigKey.SMTP_FROM_NAME.getName()), getValueByName(EmailConfigKey.SMTP_FROM_ADDRESS.getName()));

            for (EmailTo emailTo : tos) {
                emailPopulatingBuilder = emailPopulatingBuilder.to(emailTo.getName(), emailTo.getAddress());
            }

            String subject = "";
            String contentText = "";

            if (EmailSendType.VALIDATE_EMAIL_ADDRESS.equals(emailSendType)) {
                subject = getValueByName(EmailConfigKey.SMTP_SUBJECT_EMAIL_VALIDATE.getName());
                contentText = getValueByName(EmailConfigKey.SMTP_CONTENT_EMAIL_VALIDATE.getName()).replaceAll("@@link@@", content);
            } else if (EmailSendType.RESET_PASSWORD.equals(emailSendType)) {
                subject = getValueByName(EmailConfigKey.SMTP_SUBJECT_RESET_PASSWORD.getName());
                contentText = getValueByName(EmailConfigKey.SMTP_CONTENT_RESET_PASSWORD.getName()).replaceAll("@@password@@", content);
            }

            if ("HTML".equalsIgnoreCase(getValueByName(EmailConfigKey.SMTP_CONTENT_TYPE.getName()))) {
                emailPopulatingBuilder = emailPopulatingBuilder.withSubject(subject).withHTMLText(contentText);
            } else {
                emailPopulatingBuilder = emailPopulatingBuilder.withSubject(subject).withPlainText(contentText);
            }

            Email email = emailPopulatingBuilder.buildEmail();

            MailerBuilder.MailerRegularBuilder mailerRegularBuilder = MailerBuilder
                    .withSMTPServer(getValueByName(EmailConfigKey.SMTP_HOST.getName()), Integer.parseInt(getValueByName(EmailConfigKey.SMTP_PORT.getName())), getValueByName(EmailConfigKey.SMTP_USER.getName()), getValueByName(EmailConfigKey.SMTP_PASSWORD.getName()))
                    .withTransportStrategy(getTransportStrategy());
            mailerRegularBuilder.buildMailer().sendMail(email);
            return true;
        } catch (Exception e) {
            logger.error("Has error while send email. ", e);
            return false;
        }


    }

    public static String hash(String str, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(str.getBytes());
            byte[] digest = md.digest();
            String hash = DatatypeConverter.printHexBinary(digest);
            return hash;
        } catch (Exception e) {
            logger.error("Has error. ", e);
            return null;
        }

    }

    public static TransportStrategy getTransportStrategy() {
        String name = getValueByName(EmailConfigKey.SMTP_TRANSPORT_STRATEGY.getName());
        TransportStrategy transportStrategy = TransportStrategy.SMTP;
        if ("SMTP_TLS".equalsIgnoreCase(name)) {
            transportStrategy = TransportStrategy.SMTP_TLS;
        } else if ("SMTPS".equalsIgnoreCase(name)) {
            transportStrategy = TransportStrategy.SMTPS;
        }
        return transportStrategy;
    }


}
