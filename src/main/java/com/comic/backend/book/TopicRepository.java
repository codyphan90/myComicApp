package com.comic.backend.book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicEntity, Integer> {
    TopicEntity findByIdEquals(Integer id);
}
