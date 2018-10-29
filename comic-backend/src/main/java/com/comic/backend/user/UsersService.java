package com.comic.backend.user;

import com.comic.backend.reponse.ResponseEntity;
import com.comic.backend.request.LoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

import static com.comic.backend.constant.MessageConstant.*;
import static com.comic.backend.constant.SecurityConstant.EXPIRE_MINUTES;
import static com.comic.backend.constant.SecurityConstant.SECRET_KEY;


@Service
public class UsersService {

    private Logger logger = LogManager.getLogger(UsersService.class);

    @Autowired
    protected UsersRepository usersRepository;

    @Transactional
    public UserEntity create(UserEntity userEntity) {
        logger.info("Create new user with userName [{}] and userGroup [{}]", userEntity.getUserName(), userEntity.getGroupId());
        return usersRepository.save(userEntity);
    }

    public String validateUserLogin(LoginRequest request) {
        logger.info("Validate userName [{}] login", request.getUserName());

        if (StringUtils.isEmpty(request.getUserName())) return DATA_IS_BLANK;
        if (StringUtils.isEmpty(request.getUserName())) return DATA_IS_BLANK;
        UserEntity userEntity = usersRepository.findByUserNameEquals(request.getUserName());

        if (userEntity == null) return USER_NAME_OR_PASSWORD_IS_INVALID;
        if (!request.getPassword().equals(userEntity.getPassword())) return USER_NAME_OR_PASSWORD_IS_INVALID;
        return null;
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

}
