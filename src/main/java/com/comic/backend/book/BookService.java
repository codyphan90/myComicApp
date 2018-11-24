package com.comic.backend.book;

import com.comic.backend.request.BookRequest;
import com.comic.backend.utils.ControllerUtils;
import com.comic.backend.utils.DataTablePaginationResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class BookService {
    private Logger logger = LogManager.getLogger(BookController.class);

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    private  BookRepository bookRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubTopicRepository subTopicRepository;

    public DataTablePaginationResponse<BookEntity> getPage(MultiValueMap<String, String> params) {
        SimpleJpaRepository simpleJpaRepository = new SimpleJpaRepository(BookEntity.class, entityManager);
        ControllerUtils controllerUtils = new ControllerUtils<>();
        return controllerUtils.getPaginationData(simpleJpaRepository, params);
    }

    public BookEntity getBookDetail(Integer bookId) {
        return bookRepository.findByIdEquals(bookId);
    }

    @Transactional
    public BookEntity createBook(BookEntity bookEntity) {
        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        if (savedBookEntity.getChapterEntityList() != null) {
            savedBookEntity.getChapterEntityList().forEach(chapterEntity -> {
                chapterEntity.setBookId(savedBookEntity.getId());
                ChapterEntity savedChapterEntity = chapterRepository.save(chapterEntity);

                if (savedChapterEntity.getTopicEntityList() != null) {
                    savedChapterEntity.getTopicEntityList().forEach( topicEntity -> {
                        topicEntity.setChapterId(savedChapterEntity.getId());
                        TopicEntity savedTopicEntity = topicRepository.save(topicEntity);

                        if (savedTopicEntity.getSubTopicEntityList() != null) {
                            savedTopicEntity.getSubTopicEntityList().forEach(subTopicEntity -> {
                                subTopicEntity.setTopicId(savedTopicEntity.getId());
                                subTopicRepository.save(subTopicEntity);
                            });
                        }
                    });
                }
            });
        }
        return savedBookEntity;
    }

    @Transactional
    public BookEntity updateBook(BookEntity updateBookEntity) {
        BookEntity bookEntity = bookRepository.findByIdEquals(updateBookEntity.getId());
        if ( bookEntity != null) {
            deleteFullBook(bookEntity);
            BookEntity newBookEntity = buildBook(updateBookEntity);
            return createBook(newBookEntity);
        }
        return null;
    }

    private void deleteFullBook(BookEntity bookEntity) {
        bookEntity.getChapterEntityList().forEach(chapterEntity -> {
            chapterEntity.getTopicEntityList().forEach(topicEntity -> {
                topicEntity.getSubTopicEntityList().forEach(subTopicEntity -> {
                    subTopicRepository.delete(subTopicEntity);
                });
                topicRepository.delete(topicEntity);
            });
            chapterRepository.delete(chapterEntity);
        });
        bookRepository.delete(bookEntity);
    }

    private BookEntity buildBook(BookEntity  oldBookEntity) {
        BookEntity newBookEntity = new BookEntity();
        newBookEntity.setUserEntity(oldBookEntity.getUserEntity());
        newBookEntity.setFbId(oldBookEntity.getFbId());
        newBookEntity.setFbShareCount(oldBookEntity.getFbShareCount());
        newBookEntity.setFbShareUrl(oldBookEntity.getFbShareUrl());
        newBookEntity.setName(oldBookEntity.getName());
        newBookEntity.setPermission(oldBookEntity.getPermission());
        newBookEntity.setChapterEntityList(oldBookEntity.getChapterEntityList());
        return newBookEntity;
    }
}
