package com.comic.backend.request;

import com.comic.backend.book.BookEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookRequest {
    @JsonProperty("bookEntity")
    private BookEntity bookEntity;

    public BookEntity getBookEntity() {
        return bookEntity;
    }

    public void setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
    }

}
