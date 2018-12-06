package com.comic.backend.book;

import com.comic.backend.constant.Permission;
import com.comic.backend.constant.Status;
import com.comic.backend.user.UserEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book")
public class BookEntity {
    private Integer id;
    private String name;
//    private int userId;
    private Integer permission = Permission.READ_ONLY.getValue();
    private String fbShareUrl;
    private Integer fbShareCount;
    private String fbId;
    private UserEntity userEntity;
    private List<ChapterEntity> chapterEntityList;
    private Integer status = Status.ACTIVE.getValue();

    @Id
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

//    @Basic
//    @Column(name = "user_id")
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = this.userEntity.getId();
//    }

    @Basic
    @Column(name = "permission")
    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    @Basic
    @Column(name = "fb_share_url")
    public String getFbShareUrl() {
        return fbShareUrl;
    }

    public void setFbShareUrl(String fbShareUrl) {
        this.fbShareUrl = fbShareUrl;
    }

    @Basic
    @Column(name = "fb_share_count")
    public Integer getFbShareCount() {
        return fbShareCount;
    }

    public void setFbShareCount(Integer fbShareCount) {
        this.fbShareCount = fbShareCount;
    }

    @Basic
    @Column(name = "fb_id")
    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(permission, that.permission) &&
                Objects.equals(fbShareUrl, that.fbShareUrl) &&
                Objects.equals(fbShareCount, that.fbShareCount) &&
                Objects.equals(fbId, that.fbId);
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @OneToMany(mappedBy = "bookId")
    public List<ChapterEntity> getChapterEntityList() {
        return chapterEntityList;
    }

    public void setChapterEntityList(List<ChapterEntity> chapterEntityList) {
        this.chapterEntityList = chapterEntityList;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permission=" + permission +
                ", fbShareUrl='" + fbShareUrl + '\'' +
                ", fbShareCount=" + fbShareCount +
                ", fbId='" + fbId + '\'' +
                ", userEntity=" + userEntity +
                ", chapterEntityList=" + chapterEntityList +
                ", status=" + status +
                '}';
    }
}
