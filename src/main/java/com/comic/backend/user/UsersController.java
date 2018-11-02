package com.comic.backend.user;

import com.comic.backend.constant.ConfigKey;
import com.comic.backend.constant.UrlConstant;
import com.comic.backend.reponse.ResponseEntity;
import com.comic.backend.request.LoginRequest;
import com.comic.backend.request.UserNameRequest;
import com.comic.backend.utils.Common;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.comic.backend.constant.MessageConstant.*;
import static com.comic.backend.constant.MessageConstant.SYSTEM_ERROR_MESSAGE;

@CrossOrigin
@RestController
@RequestMapping(value = UrlConstant.USER_BASE_URL)
public class UsersController {
    private static Logger logger = LogManager.getLogger(UsersController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Common commonService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity create(@RequestBody UserEntity userEntity) {
        logger.info("========== Start creating new user ==========");
        try {
            String exceptionMessage = userService.validateCreateUser(userEntity);
            if (exceptionMessage != null) {
                return new ResponseEntity<>(false, exceptionMessage);
            } else {
                userEntity = userService.create(userEntity);
                logger.info("========== Finish create new user ==========");
                return new ResponseEntity<>(CREATE_SUCCESS.replaceAll("@@id@@", userEntity.getId().toString()));
            }
        } catch (Exception e) {
            logger.error(SYSTEM_ERROR_MESSAGE, e);
            return new ResponseEntity<>(false, e.getMessage());
        }

    }

    @RequestMapping(value = UrlConstant.GET_URL, method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getUser(@PathVariable("user_id") Integer userId) {
        logger.info("========== Start getting userId [{}]==========", userId);
        try {
            UserEntity userEntity = userService.getUserById(userId);
            if (userEntity != null) {
                return new ResponseEntity<>(userEntity);
            } else {
                return new ResponseEntity<>(false, USER_NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(SYSTEM_ERROR_MESSAGE, e);
            return new ResponseEntity<>(false, e.getMessage());
        }
    }

    @RequestMapping(value = UrlConstant.UPDATE_URL, method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity update(@PathVariable("user_id") Integer userId,
                          @RequestBody UserEntity userEntity) {
        logger.info("========== Start update user [{}] ==========", userEntity.getUserName());
        try {
            UserEntity updatedUserEntity = userService.update(userEntity, userId);
            if (updatedUserEntity != null) {
                return new ResponseEntity<>(UPDATE_SUCCESS);
            } else {
                return new ResponseEntity<>(false, USER_NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(SYSTEM_ERROR_MESSAGE, e);
            return new ResponseEntity<>(false, e.getMessage());
        }

    }

    @RequestMapping(value = UrlConstant.LOGIN_URL, method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> login(@RequestBody LoginRequest request) {
        logger.info("========== Start Login ==========");
        try {
            String exceptionMessage = userService.validateUserLogin(request);
            if (exceptionMessage != null) {
                return new ResponseEntity<>(false, exceptionMessage);
            } else {
                UserEntity userEntity = userService.getUserByUserName(request.getUserName());
                String token = commonService.generateToken(userEntity.getUserName(), userEntity.getId(),
                        commonService.getIntegerValue(ConfigKey.TIME_OUT_MINUTES.getName()),
                        commonService.getStringValue(ConfigKey.SECRET_LOGIN_KEY.getName()));
                logger.info("========== Finish generate token for user ==========");
                return new ResponseEntity<>(token);
            }
        } catch (Exception e) {
            logger.error(SYSTEM_ERROR_MESSAGE, e);
            return new ResponseEntity<>(false, e.getMessage());
        }
    }

    @RequestMapping(value = UrlConstant.VALIDATE_EMAIL_URL, method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> validateEmail(@RequestBody UserNameRequest request) {
        logger.info("========== Start validate email for user [{}] ==========", request.getUserName());
        String result = userService.sendEmailToValidate(request.getUserName());
        if (result != null) {
            return new ResponseEntity<>(result);
        }
        return new ResponseEntity<>(false, CAN_NOT_SEND_EMAIL);
    }

    @RequestMapping(value = UrlConstant.ACTIVE_URL, method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> activeUser(@PathVariable("token") String token) {
        String exceptionMessage = userService.validateEmailToken(token);
        if (exceptionMessage != null) {
            return new ResponseEntity<>(false, exceptionMessage);
        }
        return new ResponseEntity<>(true, ACTIVE_SUCCESS);

    }

    @RequestMapping(value = UrlConstant.RESET_PASS_URL, method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> resetPassword(@RequestBody UserNameRequest request) {
        logger.info("========== Start reset password for user [{}] ==========", request.getUserName());
        String exceptionMessage = userService.resetPassword(request.getUserName());
        if (exceptionMessage != null) {
            return new ResponseEntity<>(false, exceptionMessage);
        }
        return new ResponseEntity<>(EMAIL_RESET_PASSWORD_SENT.replaceAll("@@user@@", request.getUserName()));
    }

}
