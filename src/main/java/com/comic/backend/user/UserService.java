package com.comic.backend.user;

import com.comic.backend.constant.ConfigKey;
import com.comic.backend.constant.EmailSendType;
import com.comic.backend.request.LoginRequest;
import com.comic.backend.utils.Common;
import com.comic.backend.utils.EmailTo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.comic.backend.constant.MessageConstant.*;
import static com.comic.backend.constant.SecurityConstant.EXPIRE_MINUTES;
import static com.comic.backend.constant.SecurityConstant.SECRET_KEY;


@Service
public class UserService {
    private Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    protected UsersRepository usersRepository;

    @Autowired
    protected Common commonService;


    @Transactional
    public UserEntity create(UserEntity userEntity) {
        logger.info("Create new user with userName [{}]", userEntity.getUserName());
        userEntity.setPassword(Common.hash(userEntity.getPassword()));
        userEntity.setActive(false);
        return usersRepository.save(userEntity);
    }
    @Transactional
    public UserEntity update(UserEntity updateUserEntity, Integer userId) {
        UserEntity oldUserEntity = usersRepository.findByIdEquals(userId);
        if (oldUserEntity != null) {
            oldUserEntity = updateUserEntity(oldUserEntity, updateUserEntity);
            return usersRepository.saveAndFlush(oldUserEntity);
        } else {
            return null;
        }
    }

    public String validateUserLogin(LoginRequest request) {
        logger.info("Validate userName [{}] login", request.getUserName());

        if (StringUtils.isEmpty(request.getUserName())) return DATA_IS_BLANK;
        if (StringUtils.isEmpty(request.getUserName())) return DATA_IS_BLANK;
        UserEntity userEntity = usersRepository.findByUserNameEquals(request.getUserName());

        if (userEntity == null) return USER_NAME_OR_PASSWORD_IS_INVALID;
        String loginPassword = Common.hash(request.getPassword());
        if (!loginPassword.equals(userEntity.getPassword())) return USER_NAME_OR_PASSWORD_IS_INVALID;
        return null;
    }

    public UserEntity getUser(Integer userId) {
        return usersRepository.findByIdEquals(userId);
    }

    public String validateCreateUser(UserEntity user) {
        logger.info("validate new userName [{}]", user.getUserName());

        if (user.getUserName() == null) return DATA_IS_BLANK;
        if (user.getPassword() == null) return DATA_IS_BLANK;
        Integer entityCheckDuplicate = usersRepository.countByUserName(user.getUserName());

        if (entityCheckDuplicate > 0) return String.format(DUPLICATE_EXCEPTION_MESSAGE, "User " + user.getUserName());
        return null;
    }


    public String generateToken(String userName) {
        logger.info("Generate token for userName [{}] ", userName);
        Integer timeOut = Integer.parseInt(commonService.getValueByName(ConfigKey.TIME_OUT_MINUTES.getName()));
        return commonService.generateToken(userName, timeOut);
    }

    public void activeUser(Integer userId) {
        UserEntity user = usersRepository.findByIdEquals(userId);
        user.setActive(true);
        usersRepository.saveAndFlush(user);
    }

    public String resetPassword(String userName) {
        UserEntity userEntity = usersRepository.findByUserNameEquals(userName);
        if (userEntity != null ) {
            String resetPassword = Common.generateRandom();
            userEntity.setPassword(Common.hash(resetPassword));
            try {
                usersRepository.saveAndFlush(userEntity);
            } catch (Exception e) {
                logger.error(SYSTEM_ERROR_MESSAGE, e);
                return e.getMessage();
            }

            // send mail to user
            List<EmailTo> emailList = Arrays.asList(new EmailTo(userName, null));
            Boolean result = commonService.sendMail(EmailSendType.RESET_PASSWORD, emailList, resetPassword);
            if (result) {
                logger.info("Sent mail to [{}] successfully", userName);
                return PASSWORD_RESET;
            }
        }
        logger.error("User not found");
        return null;
    }

    public String validateEmail(String userName) {
        UserEntity userEntity = usersRepository.findByUserNameEquals(userName);
        if (userEntity != null) {
            if (userEntity.getActive())  {
                logger.info("User [{}] already active", userName);
                return null;
            }
            List<EmailTo> emailList = Arrays.asList(new EmailTo(userName, null));
            String apiLink = commonService.getValueByName(ConfigKey.SERVER_API_ACTIVE_USER.getName());
            String token = commonService.generateToken(userName, Integer.parseInt(commonService.getValueByName(ConfigKey.EXPIRE_MINUTES.getName())));
            String validateEmailURL = apiLink + userEntity.getId() + "/" + token;
            logger.info("Send email validate to user [{}]", userName);
            Boolean result = commonService.sendMail(EmailSendType.VALIDATE_EMAIL_ADDRESS, emailList, validateEmailURL);
            if (result) {
                logger.info(EMAIL_SENT);
                return EMAIL_SENT;
            }
        }
        logger.error("User not found");
        return null;
    }

    private UserEntity updateUserEntity(UserEntity oldUserEntity,UserEntity updateUserEntity) {
        oldUserEntity.setFirstName(updateUserEntity.getFirstName());
        oldUserEntity.setLastName(updateUserEntity.getLastName());
        oldUserEntity.setPassword(updateUserEntity.getPassword());
        oldUserEntity.setGroupId(updateUserEntity.getGroupId());
        return oldUserEntity;
    }

}
