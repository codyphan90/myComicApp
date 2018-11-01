package com.comic.backend.utils;

import com.comic.backend.configuration.ConfigurationEntity;
import com.comic.backend.configuration.ConfigurationRepository;
import com.comic.backend.constant.ConfigKey;
import com.comic.backend.constant.EmailSendType;
import com.comic.backend.constant.SecurityConstant;
import com.comic.backend.user.UserEntity;
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
import static com.comic.backend.constant.SecurityConstant.EXPIRE_MINUTES;
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

    public  String getValueByName(String name) {
         if (configMap == null) {
            loadConfig();
        }
        return configMap != null ? configMap.get(name) : null;
    }


    public  boolean sendMail(EmailSendType emailSendType, List<EmailTo> tos, String content) {

        try {
            EmailPopulatingBuilder emailPopulatingBuilder =
                    EmailBuilder.startingBlank()
                            .from(getValueByName(SMTP_FROM_NAME.getName()), getValueByName(SMTP_FROM_ADDRESS.getName()));

            for (EmailTo emailTo : tos) {
                emailPopulatingBuilder = emailPopulatingBuilder.to(emailTo.getName(), emailTo.getAddress());
            }

            String subject = "";
            String contentText = "";

            if (EmailSendType.VALIDATE_EMAIL_ADDRESS.equals(emailSendType)) {
                subject = getValueByName(SMTP_SUBJECT_EMAIL_VALIDATE.getName());
                contentText = getValueByName(SMTP_CONTENT_EMAIL_VALIDATE.getName()).replaceAll("@@link@@", content);
            } else if (EmailSendType.RESET_PASSWORD.equals(emailSendType)) {
                subject = getValueByName(SMTP_SUBJECT_RESET_PASSWORD.getName());
                contentText = getValueByName(SMTP_CONTENT_RESET_PASSWORD.getName()).replaceAll("@@password@@", content);
            }

            if ("HTML".equalsIgnoreCase(getValueByName(SMTP_CONTENT_TYPE.getName()))) {
                emailPopulatingBuilder = emailPopulatingBuilder.withSubject(subject).withHTMLText(contentText);
            } else {
                emailPopulatingBuilder = emailPopulatingBuilder.withSubject(subject).withPlainText(contentText);
            }

            Email email = emailPopulatingBuilder.buildEmail();

            MailerBuilder.MailerRegularBuilder mailerRegularBuilder = MailerBuilder
                    .withSMTPServer(getValueByName(SMTP_HOST.getName()),
                            Integer.parseInt(getValueByName(SMTP_PORT.getName())),
                            getValueByName(SMTP_USER.getName()),
                            getValueByName(SMTP_PASSWORD.getName()))
                    .withTransportStrategy(getTransportStrategy());

            mailerRegularBuilder.buildMailer().sendMail(email);
            return true;
        } catch (Exception e) {
            logger.error("Has error while send email. ", e);
            return false;
        }


    }

    public  TransportStrategy getTransportStrategy() {
        String name = getValueByName(SMTP_TRANSPORT_STRATEGY.getName());
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
            String hash = DatatypeConverter.printHexBinary(digest);
            return hash;
        } catch (Exception e) {
            logger.error("Has error. ", e);
            return null;
        }

    }

    public static String generateRandom() {
        return RandomStringUtils.randomAlphabetic(8);
    }

    public String generateToken(String userName, Integer minutes) {
        Date expireDate = new Date(System.currentTimeMillis() + minutes * 60 * 1000);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .setSubject(userName)
                .setExpiration(expireDate)
                .signWith(signatureAlgorithm, signingKey)
                .compact();
    }

    public static Claims decodeToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token).getBody();
//        System.out.println("ID: " + claims.getId());
//        System.out.println("Subject: " + claims.getSubject());
//        System.out.println("Issuer: " + claims.getIssuer());
//        System.out.println("Expiration: " + claims.getExpiration());
        return claims;
    }

    public static Boolean validateToken(UserEntity user, String token) {
        Claims claims = decodeToken(token);
        //to-do: verify expire time
        Date expireDateToken = claims.getExpiration();

        return (user.getUserName().equals(claims.getSubject()));
    }

}
