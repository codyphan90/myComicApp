package com.comic.backend.book;

import com.comic.backend.constant.Status;
import com.comic.backend.user.UserEntity;
import com.comic.backend.user.UsersRepository;
import com.comic.backend.utils.ControllerUtils;
import com.comic.backend.utils.DataTablePaginationResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private UsersRepository usersRepository;

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
        if (bookEntity.getId() == null) {
            bookEntity = bookRepository.save(bookEntity);
        }
        Integer bookId = bookEntity.getId();
        if (bookEntity.getChapterEntityList() != null) {
            bookEntity.getChapterEntityList().forEach(chapterEntity -> {
                chapterEntity.setBookId(bookId);
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
        return bookEntity;
    }

    @Transactional
    public BookEntity updateBook(BookEntity updateBookEntity) {
        BookEntity bookEntity = bookRepository.findByIdEquals(updateBookEntity.getId());
        if ( bookEntity != null) {
            deleteDetailOfBook(bookEntity);
            bookEntity = cloneBook(updateBookEntity, bookEntity);
            List<ChapterEntity> chapterEntities = buildChaptersFromBook(updateBookEntity);
            bookEntity.setChapterEntityList(chapterEntities);
            return createBook(bookEntity);
        }
        return null;
    }

    private void deleteDetailOfBook(BookEntity bookEntity) {
        bookEntity.getChapterEntityList().forEach(chapterEntity -> {
            chapterEntity.getTopicEntityList().forEach(topicEntity -> {
                topicEntity.getSubTopicEntityList().forEach(subTopicEntity -> {
                    subTopicRepository.delete(subTopicEntity);
                });
                topicRepository.delete(topicEntity);
            });
            chapterRepository.delete(chapterEntity);
        });
    }

    private List<ChapterEntity> buildChaptersFromBook(BookEntity bookEntity) {
        List<ChapterEntity> chapterEntities = new ArrayList<>();
        if (bookEntity.getChapterEntityList() != null) {
            bookEntity.getChapterEntityList().forEach(chapterEntity -> {
                ChapterEntity newChapter = buildChapter(chapterEntity);
                List<TopicEntity> topicEntities = new ArrayList<>();
                if (chapterEntity.getTopicEntityList() != null) {
                    chapterEntity.getTopicEntityList().forEach(topicEntity -> {
                        TopicEntity newTopicEntity = buildTopic(topicEntity);
                        List<SubTopicEntity> subTopicEntities = new ArrayList<>();
                        if (topicEntity.getSubTopicEntityList() != null) {
                            topicEntity.getSubTopicEntityList().forEach(subTopicEntity -> {
                                SubTopicEntity newSubTopicEntity = buildSubTopic(subTopicEntity);
                                subTopicEntities.add(newSubTopicEntity);
                            });
                        }
                        newTopicEntity.setSubTopicEntityList(subTopicEntities);
                        topicEntities.add(newTopicEntity);
                    });
                }
                newChapter.setTopicEntityList(topicEntities);
                chapterEntities.add(newChapter);
            });
        }
        return chapterEntities;
    }

    public BookEntity copyBook(Integer bookId, Integer userId) {
        UserEntity userEntity = usersRepository.findByIdEquals(userId);
        if (userEntity != null) {
            userEntity.setId(userId);
            BookEntity bookEntity = bookRepository.findByIdEquals(bookId);
            BookEntity newBook = buildNewBook(bookEntity);
            newBook.setUserEntity(userEntity);
            newBook.setChapterEntityList(buildChaptersFromBook(bookEntity));
            return createBook(newBook);
        }
        return null;
    }

    private BookEntity buildNewBook(BookEntity oldBookEntity) {
        BookEntity newBookEntity = new BookEntity();
        cloneBook(oldBookEntity, newBookEntity);
        return newBookEntity;
    }

    private BookEntity cloneBook(BookEntity sourceBook, BookEntity cloneBook) {
        cloneBook.setUserEntity(sourceBook.getUserEntity());
        cloneBook.setFbId(sourceBook.getFbId());
        cloneBook.setFbShareCount(sourceBook.getFbShareCount());
        cloneBook.setFbShareUrl(sourceBook.getFbShareUrl());
        cloneBook.setName(sourceBook.getName());
//        cloneBook.setPermission(sourceBook.getPermission());
        cloneBook.setStatus(sourceBook.getStatus());
        return cloneBook;
    }

    private ChapterEntity buildChapter(ChapterEntity chapterEntity) {
        ChapterEntity newChapter = new ChapterEntity();
        newChapter.setName(chapterEntity.getName());
        newChapter.setDescription(chapterEntity.getDescription());
        return newChapter;
    }

    private TopicEntity buildTopic(TopicEntity topicEntity) {
        TopicEntity newTopic = new TopicEntity();
        newTopic.setName(topicEntity.getName());
        newTopic.setDescription(topicEntity.getDescription());
        return newTopic;
    }

    private SubTopicEntity buildSubTopic(SubTopicEntity subTopicEntity) {
        SubTopicEntity newSubTopic = new SubTopicEntity();
        newSubTopic.setName(subTopicEntity.getName());
        newSubTopic.setDescription(subTopicEntity.getDescription());
        newSubTopic.setContent(subTopicEntity.getContent());
        return newSubTopic;
    }

    @Transactional
    public void softDeleteBook(BookEntity bookEntity) {
        bookEntity.setStatus(Status.IS_DELETED.getValue());
        bookRepository.save(bookEntity);
    }
}
