package com.comic.backend.user;

import com.comic.backend.constant.UrlConstant;
import com.comic.backend.reponse.ResponseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.comic.backend.constant.MessageConstant.SYSTEM_ERROR_MESSAGE;

@CrossOrigin
@RestController
@RequestMapping(value = UrlConstant.USER_GROUP_BASE_URL)
public class UserGroupController {
    private static Logger logger = LogManager.getLogger(UsersController.class);

    @Autowired
    private UserGroupService userGroupService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getUserGroups() {
        logger.info("========== Start getting user group list ==========");
        try {
            List<UserGroupEntity> userGroupEntityList = userGroupService.getUserGroups();
            return new ResponseEntity<>(userGroupEntityList);
        } catch (Exception e) {
            logger.error(SYSTEM_ERROR_MESSAGE, e);
            throw e;
        }
    }
}
