package com.pmp.hrsrc.entity;

import java.io.Serializable;

public class HandBook implements Serializable {

    private int id;
    private int segid;
    private String books;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSegid() {
        return segid;
    }

    public void setSegid(int segid) {
        this.segid = segid;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }
}
