package com.comic.backend.utils;

/**
 * Created by datha on 4/6/2018.
 */
public class DataTableColumn {
    private int index;
    private String data;
    private String name;
    private String searchValue;

    public DataTableColumn(int index, String data, String name, String searchValue) {
        this.index = index;
        this.data = data;
        this.name = name;
        this.searchValue = searchValue;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getData() {
        if (data == null || data.isEmpty()) {
            data = "id";
        }
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    @Override
    public String toString() {
        return "DataTableColumn{" +
                "index=" + index +
                ", data='" + data + '\'' +
                ", name='" + name + '\'' +
                ", searchValue='" + searchValue + '\'' +
                '}';
    }
}
