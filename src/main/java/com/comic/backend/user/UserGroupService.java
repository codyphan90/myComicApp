package com.comic.backend.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupService {
    private Logger logger = LogManager.getLogger(UserGroupService.class);

    @Autowired
    protected UserGroupRepository userGroupRepository;

    public List<UserGroupEntity> getUserGroups() {
        return userGroupRepository.findAll();
    }
}
