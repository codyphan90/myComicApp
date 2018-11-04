package com.comic.backend.utils;

import com.comic.backend.configuration.ConfigurationEntity;
import com.comic.backend.configuration.ConfigurationRepository;
import com.comic.backend.constant.ConfigKey;
import com.comic.backend.constant.EmailSendType;
import com.comic.backend.constant.SecurityConstant;
import com.comic.backend.user.UserEntity;
import com.comic.backend.user.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.email.EmailPopulatingBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.MessageDigest;
import java.util.*;

import static com.comic.backend.constant.ConfigKey.*;
import static com.comic.backend.constant.SecurityConstant.SECRET_KEY;

@Service
public class Common {

    private static Logger logger = LogManager.getLogger(Common.class);

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

    public  String getStringValue(String name) {
         if (configMap == null) loadConfig();
        return configMap.get(name);
    }

    public Integer getIntegerValue(String name) {
        if (configMap == null) loadConfig();
        try {
            String valueString = configMap.get(name);
            if (valueString != null) {
                return Integer.parseInt(valueString);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }


    public  boolean sendMail(EmailSendType emailSendType, List<EmailTo> tos, String content) {

        try {
            EmailPopulatingBuilder emailPopulatingBuilder =
                    EmailBuilder.startingBlank()
                            .from(getStringValue(SMTP_FROM_NAME.getName()), getStringValue(SMTP_FROM_ADDRESS.getName()));

            for (EmailTo emailTo : tos) {
                emailPopulatingBuilder = emailPopulatingBuilder.to(emailTo.getName(), emailTo.getAddress());
            }

            String subject = "";
            String contentText = "";

            if (EmailSendType.VALIDATE_EMAIL_ADDRESS.equals(emailSendType)) {
                subject = getStringValue(SMTP_SUBJECT_EMAIL_VALIDATE.getName());
                contentText = getStringValue(SMTP_CONTENT_EMAIL_VALIDATE.getName()).replaceAll("@@link@@", content);
            } else if (EmailSendType.RESET_PASSWORD.equals(emailSendType)) {
                subject = getStringValue(SMTP_SUBJECT_RESET_PASSWORD.getName());
                contentText = getStringValue(SMTP_CONTENT_RESET_PASSWORD.getName()).replaceAll("@@password@@", content);
            }

            if ("HTML".equalsIgnoreCase(getStringValue(SMTP_CONTENT_TYPE.getName()))) {
                emailPopulatingBuilder = emailPopulatingBuilder.withSubject(subject).withHTMLText(contentText);
            } else {
                emailPopulatingBuilder = emailPopulatingBuilder.withSubject(subject).withPlainText(contentText);
            }

            Email email = emailPopulatingBuilder.buildEmail();

            MailerBuilder.MailerRegularBuilder mailerRegularBuilder = MailerBuilder
                    .withSMTPServer(getStringValue(SMTP_HOST.getName()),
                            getIntegerValue(SMTP_PORT.getName()),
                            getStringValue(SMTP_USER.getName()),
                            getStringValue(SMTP_PASSWORD.getName()))
                    .withTransportStrategy(getTransportStrategy());

            mailerRegularBuilder.buildMailer().sendMail(email);
            return true;
        } catch (Exception e) {
            logger.error("Has error while send email. ", e);
            return false;
        }


    }

    public  TransportStrategy getTransportStrategy() {
        String name = getStringValue(SMTP_TRANSPORT_STRATEGY.getName());
        TransportStrategy transportStrategy = TransportStrategy.SMTP;
        if ("SMTP_TLS".equalsIgnoreCase(name)) {
            transportStrategy = TransportStrategy.SMTP_TLS;
        } else if ("SMTPS".equalsIgnoreCase(name)) {
            transportStrategy = TransportStrategy.SMTPS;
        }
        System.out.println("transport: " + transportStrategy);
        return transportStrategy;
    }

    public static String hash(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance(SecurityConstant.PASSWORD_HASH_ALGORITHM);
            md.update(str.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest);
        } catch (Exception e) {
            logger.error("Has error. ", e);
            return null;
        }

    }

    public static String generateRandom() {
        return RandomStringUtils.randomAlphabetic(8);
    }

    public String generateToken(String userName, Integer userId, Integer minutes, String secret) {
        Date expireDate = new Date(System.currentTimeMillis() + minutes * 60 * 1000);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .setId(userId.toString())
                .setSubject(userName)
                .setExpiration(expireDate)
                .signWith(signatureAlgorithm, signingKey)
                .compact();
    }

    public static Claims decodeToken(String token, String secret) {
        try {

            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                    .parseClaimsJws(token).getBody();
            return claims;
        }catch (Exception e) {
            logger.error("Can not decode token: ",e);
            return null;
        }
    }
}
