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
    private BookRepository bookRepository;

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
                    savedChapterEntity.getTopicEntityList().forEach(topicEntity -> {
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



    public BookEntity saveOrUpdateBook(BookEntity updateBookEntity, BookEntity bookDB) {
        BookEntity bookFromDB = bookRepository.findByIdEquals(updateBookEntity.getId());
        if (bookFromDB != null) {
            bookFromDB = updateBookEntity;
            bookFromDB = bookRepository.save(bookFromDB);

            Integer bookId = bookFromDB.getId();
            if (updateBookEntity.getChapterEntityList() != null) {
                updateBookEntity.getChapterEntityList().forEach(chapterEntity -> {
                    ChapterEntity chapterEntityToSave = chapterEntity;
                    chapterEntityToSave.setBookId(bookId);
                    ChapterEntity updateChapterEntity = chapterRepository.save(chapterEntityToSave);
                    if (chapterEntity.getTopicEntityList() != null) {
                        chapterEntity.getTopicEntityList().forEach(topicEntity -> {
                            TopicEntity topicEntityToSave = topicEntity;
                            topicEntityToSave.setChapterId(updateChapterEntity.getId());
                            TopicEntity updateTopicEntity = topicRepository.save(topicEntityToSave);
                            if (topicEntity.getSubTopicEntityList() != null) {
                                topicEntity.getSubTopicEntityList().forEach(subTopicEntity -> {
                                    SubTopicEntity subTopicEntityToSave = subTopicEntity;
                                    subTopicEntityToSave.setTopicId(updateTopicEntity.getId());
                                    subTopicRepository.save(subTopicEntityToSave);
                                });
                            }
                        });
                    }
                });
            }
            return bookFromDB;
        }

        return null;
    }


    @Transactional
    public BookEntity updateBook(BookEntity updateBookEntity) {
        BookEntity bookFromDB = bookRepository.findByIdEquals(updateBookEntity.getId());

        if (bookFromDB != null) {
            List<Integer> listChapterIdFromDB = getListChapterId(bookFromDB);
            List<Integer> listChapterIdFromRequest = getListChapterId(updateBookEntity);
            List<ChapterEntity> listDeleteChapter = new ArrayList<>();
            List<TopicEntity> listDeleteTopic = new ArrayList<>();
            List<SubTopicEntity> listDeleteSubTopic = new ArrayList<>();

            listChapterIdFromDB.forEach(id -> {
                if (!listChapterIdFromRequest.contains(id)) {
                    ChapterEntity removeChapter = chapterRepository.findByIdEquals(id);
                    listDeleteChapter.add(removeChapter);
                }
            });

            updateBookEntity.getChapterEntityList().forEach(chapterEntity -> {
                ChapterEntity chapterEntityDB = chapterRepository.findByIdEquals(chapterEntity.getId());
                if (chapterEntityDB != null) {
                    List<Integer> listTopicIdDB = getListTopicId(chapterEntityDB);
                    List<Integer> listTopicIdRequest = getListTopicId(chapterEntity);
                    listTopicIdDB.forEach(id -> {
                        if (!listTopicIdRequest.contains(id)) {
                            TopicEntity removeTopic = topicRepository.findByIdEquals(id);
                            listDeleteTopic.add(removeTopic);
                        }
                    });
                }
            });

            List<Integer> subSubTopicIdFromRequest = getListSubTopicId(updateBookEntity);
            List<Integer> subTopicIdFromDB = getListSubTopicId(bookFromDB);

            subTopicIdFromDB.forEach(id -> {
                if (!subSubTopicIdFromRequest.contains(id)) {
                    SubTopicEntity deleteSubTopic = subTopicRepository.findByIdEquals(id);
                    if (deleteSubTopic != null) {
                        listDeleteSubTopic.add(deleteSubTopic);
                    }
                }
            });

            saveOrUpdateBook(updateBookEntity, bookFromDB);

            subTopicRepository.deleteAll(listDeleteSubTopic);
            chapterRepository.deleteAll(listDeleteChapter);
            topicRepository.deleteAll(listDeleteTopic);



            return bookFromDB;
        }
        return null;
    }

    private void rebuildBook(BookEntity bookFromRequest, BookEntity bookFromDB) {
        bookFromRequest.getChapterEntityList().forEach(chapterFromRequest -> {
            if (chapterFromRequest.getId() != null) {
                bookFromDB.getChapterEntityList().forEach(chapterFromDB -> {
                    if (chapterFromDB.getId().equals(chapterFromRequest.getId())) {
                        chapterFromDB.setName(chapterFromRequest.getName());
//                        chapterRepository.save(chapterFromDB);
                    }
                    Integer chapterId = chapterFromRequest.getId();
                    chapterFromRequest.getTopicEntityList().forEach(topicFromRequest -> {
                        if (topicFromRequest.getId() != null) {
                            chapterFromDB.getTopicEntityList().forEach(topicFromDB -> {
                                if (topicFromDB.getId().equals(topicFromRequest.getId())) {
                                    topicFromDB.setName(topicFromRequest.getName());
//                                    topicRepository.save(topicFromDB);
                                }
                                Integer topicId = topicFromRequest.getId();
                                topicFromRequest.getSubTopicEntityList().forEach(subTopicFromRequest -> {
                                    if (subTopicFromRequest.getId() != null) {
                                        topicFromDB.getSubTopicEntityList().forEach(subTopicFromDB -> {
                                            if (subTopicFromDB.getId().equals(subTopicFromRequest.getId())) {
                                                subTopicFromDB.setName(subTopicFromRequest.getName());
                                                subTopicFromDB.setContent(subTopicFromRequest.getContent());
//                                               subTopicRepository.save(subTopicFromDB);
                                            }
                                        });
                                    } else {
                                        SubTopicEntity subTopicEntity = new SubTopicEntity();
                                        subTopicEntity.setTopicId(topicId);
                                        subTopicEntity.setName(subTopicFromRequest.getName());
                                        subTopicEntity.setContent(subTopicFromRequest.getContent());
                                        subTopicRepository.save(subTopicEntity);
                                        topicFromDB.getSubTopicEntityList().add(subTopicEntity);
                                    }
                                });
                            });

                        } else {
                            TopicEntity topicEntity = new TopicEntity();
                            topicEntity.setChapterId(chapterId);
                            topicEntity.setName(topicFromRequest.getName());
                            topicRepository.save(topicEntity);
                            chapterFromDB.getTopicEntityList().add(topicEntity);
                        }
                    });
                });
            } else {
                ChapterEntity chapterEntity = new ChapterEntity();
                chapterEntity.setBookId(bookFromRequest.getId());
                chapterEntity.setName(chapterFromRequest.getName());
                chapterRepository.save(chapterEntity);
                bookFromDB.getChapterEntityList().add(chapterEntity);
            }
        });
    }

    @Transactional
    public BookEntity updateBook2(BookEntity updateBookEntity) {
        BookEntity bookEntity = bookRepository.findByIdEquals(updateBookEntity.getId());
        if (bookEntity != null) {
            bookEntity.getChapterEntityList().forEach(chapterEntity -> {
                chapterRepository.delete(chapterEntity);
            });
            bookRepository.save(bookEntity);

            bookEntity.setChapterEntityList(updateBookEntity.getChapterEntityList());
            return createBook(bookEntity);
        }
        return null;
    }

    private List<Integer> getListSubTopicId(BookEntity bookEntity) {
        List<Integer> listSubTopicId = new ArrayList<>();
        bookEntity.getChapterEntityList().forEach(chapterEntity -> {
            chapterEntity.getTopicEntityList().forEach(topicEntity -> {
                topicEntity.getSubTopicEntityList().forEach(subTopicEntity -> {
                    listSubTopicId.add(subTopicEntity.getId());
                });
            });
        });
        return listSubTopicId;
    }

    private List<Integer> getListChapterId(BookEntity bookEntity) {
        List<Integer> listChapterId = new ArrayList<>();
        bookEntity.getChapterEntityList().forEach(chapterEntity -> {
            listChapterId.add(chapterEntity.getId());
        });
        return listChapterId;
    }

    private List<Integer> getListTopicId(ChapterEntity chapterEntity) {
        List<Integer> listTopicId = new ArrayList<>();
        chapterEntity.getTopicEntityList().forEach(topicEntity -> {
            listTopicId.add(topicEntity.getId());
        });
        return listTopicId;
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

    @Transactional
    public void deleteChapter(Integer id) {
        ChapterEntity chapterEntity = chapterRepository.findByIdEquals(id);
        chapterRepository.delete(chapterEntity);
    }
}
