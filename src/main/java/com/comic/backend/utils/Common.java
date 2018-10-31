package com.comic.backend.utils;

import com.comic.backend.configuration.ConfigurationEntity;
import com.comic.backend.configuration.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Common {

    @Autowired
    private ConfigurationRepository configurationRepository;


    private  static Map<String, String> configMap = null;

    public  Map<String, String> getConfigMap() {
        if (configMap == null) {
            configMap = new HashMap<>();
            List<ConfigurationEntity> configurationEntities = configurationRepository.findAll();
            for (ConfigurationEntity configurationEntity : configurationEntities) {
                configMap.put(configurationEntity.getKeyName(), configurationEntity.getKeyValue());
            }
        }
        return configMap;
    }


    public static void sendMail() {

    }


}
