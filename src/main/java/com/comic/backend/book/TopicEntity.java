package com.comic.backend.book;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "topic", catalog = "")
public class TopicEntity {
    private Integer id;
    private String name;
    private Integer chapterId;
    private String description;

    private List<SubTopicEntity> subTopicEntityList;

    @Id
    @Basic
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "chapter_id")
    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubTopicEntityList(List<SubTopicEntity> subTopicEntityList) {
        this.subTopicEntityList = subTopicEntityList;
    }

    @OneToMany(mappedBy = "topicId")
    public List<SubTopicEntity> getSubTopicEntityList() {
        return subTopicEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TopicEntity that = (TopicEntity) o;

        if (id != that.id) return false;
        if (chapterId != that.chapterId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + chapterId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
