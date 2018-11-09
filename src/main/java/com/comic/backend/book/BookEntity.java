package com.comic.backend.book;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "book", schema = "comic", catalog = "")
public class BookEntity {
    private int id;
    private String name;
    private int userId;
    private Integer permission;
    private String fbShareUrl;
    private Integer fbShareCount;
    private String fbId;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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
                userId == that.userId &&
                Objects.equals(name, that.name) &&
                Objects.equals(permission, that.permission) &&
                Objects.equals(fbShareUrl, that.fbShareUrl) &&
                Objects.equals(fbShareCount, that.fbShareCount) &&
                Objects.equals(fbId, that.fbId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userId, permission, fbShareUrl, fbShareCount, fbId);
    }
}
