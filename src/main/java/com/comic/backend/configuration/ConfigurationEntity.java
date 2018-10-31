package com.comic.backend.configuration;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "configuration", schema = "comic", catalog = "")
public class ConfigurationEntity {
    private int id;
    private String keyName;
    private String keyValue;

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
    @Column(name = "key_name")
    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Basic
    @Column(name = "key_value")
    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigurationEntity that = (ConfigurationEntity) o;
        return id == that.id &&
                Objects.equals(keyName, that.keyName) &&
                Objects.equals(keyValue, that.keyValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keyName, keyValue);
    }
}
