package com.comic.backend.user;

import com.comic.backend.constant.EmailSendType;
import com.comic.backend.constant.SecurityConstant;
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

import java.util.ArrayList;
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
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_MINUTES * 60 * 1000);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        return Jwts.builder()
                .setSubject(userName)
                .setExpiration(expireDate)
                .signWith(signatureAlgorithm, SECRET_KEY)
                .compact();
    }

    public void validateEmail(Integer userId) {
        UserEntity user = usersRepository.findByIdEquals(userId);
        user.setActive(true);
        usersRepository.saveAndFlush(user);
    }

    public String resetPassword(String userName) {
        UserEntity userEntity = usersRepository.findByUserNameEquals(userName);
        if (userEntity != null ) {
            String resetPassword = Common.generateRandom();
            logger.info("@@@ Reset Pass = [{}]", resetPassword);
            // to-do: sent to user mail
            List<EmailTo> emailList = new ArrayList<>();
            EmailTo emailTo = new EmailTo(userName, null);
            emailList.add(emailTo);
            Boolean result = commonService.sendMail(EmailSendType.RESET_PASSWORD, emailList,  resetPassword);
            if (result) {
                userEntity.setPassword(Common.hash(resetPassword));
                usersRepository.saveAndFlush(userEntity);
                return PASSWORD_RESET;
            }
        }
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
