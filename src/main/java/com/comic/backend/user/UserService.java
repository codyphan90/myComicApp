package com.comic.backend.user;

import com.comic.backend.constant.ConfigKey;
import com.comic.backend.constant.EmailSendType;
import com.comic.backend.constant.MessageConstant;
import com.comic.backend.reponse.ResponseEntity;
import com.comic.backend.request.LoginRequest;
import com.comic.backend.request.UpdateUserRequest;
import com.comic.backend.utils.Common;
import com.comic.backend.utils.EmailTo;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.comic.backend.constant.ConfigKey.SECRET_ACTIVE_KEY;
import static com.comic.backend.constant.MessageConstant.*;


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
    public ResponseEntity update(UpdateUserRequest updateUserRequest, String userName) {
        UserEntity userEntity = usersRepository.findByUserNameEquals(userName);
        if (userEntity != null) {
            if (updateUserRequest.getNewPassword()!= null && updateUserRequest.getOldPassword() != null) {
                if (Common.hash(updateUserRequest.getOldPassword()).equals(userEntity.getPassword())) {
                    userEntity.setPassword(Common.hash(updateUserRequest.getNewPassword()));
                } else {
                    return new ResponseEntity(false, PASSWORD_NOT_MATCH);
                }
            }
            userEntity = updateUserEntity(userEntity, updateUserRequest);
            usersRepository.saveAndFlush(userEntity);
            return new ResponseEntity<> (UPDATE_SUCCESS);
        } else {
            return new ResponseEntity<>(false,USER_NOT_FOUND);
        }
    }

    private UserEntity updateUserEntity(UserEntity userEntity, UpdateUserRequest updateUserEntity) {
        userEntity.setFirstName(updateUserEntity.getFirstName());
        userEntity.setLastName(updateUserEntity.getLastName());
        userEntity.setGroupId(updateUserEntity.getGroupId());
        return userEntity;
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

    public UserEntity getUserById(Integer userId) {
        return usersRepository.findByIdEquals(userId);
    }

    public UserEntity getUserByUserName(String userName) {
        return usersRepository.findByUserNameEquals(userName);
    }

    public String validateCreateUser(UserEntity user) {
        logger.info("validate new userName [{}]", user.getUserName());

        if (user.getUserName() == null) return DATA_IS_BLANK;
        if (user.getPassword() == null) return DATA_IS_BLANK;
        Integer entityCheckDuplicate = usersRepository.countByUserName(user.getUserName());

        if (entityCheckDuplicate > 0) return String.format(DUPLICATE_EXCEPTION_MESSAGE, "User " + user.getUserName());
        return null;
    }

    public String resetPassword(String userName) {
        UserEntity userEntity = usersRepository.findByUserNameEquals(userName);
        if (userEntity != null) {
            if (!userEntity.getActive()) {
                logger.error(USER_NOT_VALIDATE_YET);
                return USER_NOT_VALIDATE_YET;
            }
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
                return null;
            }
        }
        logger.error("User not found");
        return MessageConstant.USER_NOT_FOUND;
    }

    public String sendEmailToValidate(String userName) {
        UserEntity userEntity = usersRepository.findByUserNameEquals(userName);
        if (userEntity != null) {
            if (userEntity.getActive()) {
                logger.info("User [{}] already validate email", userName);
                return null;
            }
            List<EmailTo> emailList = Arrays.asList(new EmailTo(userName, null));
            String tokenActiveUser = commonService.generateToken(userEntity.getUserName(), userEntity.getId(),
                    commonService.getIntegerValue(ConfigKey.EXPIRE_MINUTES.getName()),
                    commonService.getStringValue(ConfigKey.SECRET_ACTIVE_KEY.getName()));
            String validateEmailURL = commonService.getStringValue(ConfigKey.WEB_VALIDATE_EMAIL.getName()).replaceAll("@@token@@", tokenActiveUser);

            logger.info("Send email validate to user [{}]", userName);
            Boolean result = commonService.sendMail(EmailSendType.VALIDATE_EMAIL_ADDRESS, emailList, validateEmailURL);
            if (result) {
                logger.info(EMAIL_SENT.replaceAll("@@user@@", userName));
                return EMAIL_SENT.replaceAll("@@user@@", userName);
            }
        }
        logger.error("User not found");
        return null;
    }

    public String validateEmailToken(String token) {
        Claims claims = Common.decodeToken(token, commonService.getStringValue(SECRET_ACTIVE_KEY.getName()));
        try {
            if (claims != null) {
                UserEntity userEntity = usersRepository.findByIdEquals(Integer.parseInt(claims.getId()));
                Date expireDate = claims.getExpiration();
                Date now = new Date(System.currentTimeMillis());
                if (now.before(expireDate)) {
                    logger.info("Token is valid");
                    userEntity.setActive(true);
                    usersRepository.saveAndFlush(userEntity);
                    return null;
                }
                logger.error("Token is Expired");
                return TOKEN_EXPIRED;
            }
            return TOKEN_IS_INVALID;
        } catch (Exception e) {
            logger.error("Token is invalid");
            return TOKEN_IS_INVALID;
        }
    }

}
