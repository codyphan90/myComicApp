package com.comic.backend.request;

import com.comic.backend.book.BookEntity;
import com.comic.backend.book.ChapterEntity;
import com.comic.backend.book.SubTopicEntity;
import com.comic.backend.book.TopicEntity;
import com.comic.backend.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BookRequest {
    @JsonProperty("userEntity")
    private UserEntity userEntity;

    @JsonProperty("bookEntity")
    private BookEntity bookEntity;


    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public BookEntity getBookEntity() {
        return bookEntity;
    }

    public void setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
    }

}
