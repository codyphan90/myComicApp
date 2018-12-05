package com.comic.backend.book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubTopicRepository extends JpaRepository<SubTopicEntity, Integer> {
    SubTopicEntity findByIdEquals(Integer id);
}
