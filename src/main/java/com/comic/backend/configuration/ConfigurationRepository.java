package com.comic.backend.configuration;

import com.comic.backend.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, Integer> {


}
