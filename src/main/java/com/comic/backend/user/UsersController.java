package com.comic.backend.user;

import com.comic.backend.constant.MessageConstant;
import com.comic.backend.constant.UrlConstant;
import com.comic.backend.reponse.ResponseEntity;
import com.comic.backend.request.LoginRequest;
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

    @RequestMapping(value = UrlConstant.CREATE_URL, method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity create(@RequestBody UserEntity userEntity) {
        logger.info("========== Start creating new user ==========");
        try {
            String exceptionMessage = userService.validateCreateUser(userEntity);
            if (exceptionMessage != null) {
                return new ResponseEntity<>(false, exceptionMessage, null);
            } else {
                userEntity = userService.create(userEntity);
                logger.info("========== Finish create new user ==========");
                return new ResponseEntity<>(true, null, CREATE_SUCCESS + userEntity.getId());
            }
        } catch (Exception e) {
            logger.error(SYSTEM_ERROR_MESSAGE + e.getMessage());
            return new ResponseEntity<>(false, e.getMessage());
        }

    }

    @RequestMapping(value = UrlConstant.GET_URL, method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getUser(@PathVariable("user_id") Integer userId) {
        logger.info("========== Start getting userId [{}]==========", userId);
        try {
            UserEntity userEntity = userService.getUser(userId);
            if (userEntity != null) {
                return new ResponseEntity<>(userEntity);
            } else {
                return new ResponseEntity<>(false, null, null);
            }
        } catch (Exception e) {
            logger.error(SYSTEM_ERROR_MESSAGE +  e.getMessage());
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
                return new ResponseEntity<>(true, null, UPDATE_SUCCESS);
            } else {
                return new ResponseEntity<>(false, null, USER_NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(SYSTEM_ERROR_MESSAGE +  e);
            return new ResponseEntity<>(false, e.getMessage(), null);
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
                String token = userService.generateToken(request.getUserName());
                logger.info("========== Finish generate token for user ==========");
                return new ResponseEntity<>(token);
            }
        } catch (Exception e) {
            logger.error(SYSTEM_ERROR_MESSAGE + e.getMessage());
            return new ResponseEntity<>(false, e.getMessage());
        }
    }

    @RequestMapping(value = UrlConstant.ACTIVE_URL, method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> active(@PathVariable("user_id") Integer userId) {
        logger.info("========== Active user id [{}] ==========", userId);
        userService.validateEmail(userId);
        return new ResponseEntity<>(true, null, VALIDATE_SUCCESS);
    }


}
