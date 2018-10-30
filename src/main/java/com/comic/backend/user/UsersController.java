package com.comic.backend.user;

import com.comic.backend.constant.UrlConstant;
import com.comic.backend.reponse.ResponseEntity;
import com.comic.backend.request.LoginRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.comic.backend.constant.MessageConstant.SYSTEM_ERROR_MESSAGE;

@CrossOrigin
@RestController
@RequestMapping(value = UrlConstant.USER_BASE_URL)
public class UsersController {
    private static Logger logger = LogManager.getLogger(UsersController.class);

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = UrlConstant.CREATE_URL, method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity create(@RequestBody UserEntity userEntity) {
        logger.info("========== Start creating new user ==========",
                userEntity.getUserName(), userEntity.getGroupId());
        try {
            String exceptionMessage = usersService.validateCreateUser(userEntity);
            if (exceptionMessage != null) {
                return new ResponseEntity<>(false, exceptionMessage, null);
            } else {
                userEntity = usersService.create(userEntity);
                logger.info("========== Finish create new user ==========");
                return new ResponseEntity<>(true, null, userEntity.getId());
            }
        } catch (Exception e) {
            logger.error(SYSTEM_ERROR_MESSAGE, e.getMessage());
            return new ResponseEntity<>(false, e.getMessage());
        }

    }

    @RequestMapping(value = UrlConstant.LOGIN_URL, method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> login(@RequestBody LoginRequest request) {
        logger.info("========== Start Login ==========");
        try {
            String exceptionMessage = usersService.validateUserLogin(request);
            if (exceptionMessage != null) {
                return new ResponseEntity<>(false, exceptionMessage);
            } else {
                String token = usersService.generateToken(request.getUserName());
                logger.info("========== Finish generate token for user ==========");
                return new ResponseEntity<>(token);
            }
        } catch (Exception e) {
            logger.error(SYSTEM_ERROR_MESSAGE, e.getMessage());
            return new ResponseEntity<>(false, e.getMessage());
        }
    }


}
