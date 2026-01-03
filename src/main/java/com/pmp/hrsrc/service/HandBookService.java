package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.HandBook;

import java.util.List;

public interface HandBookService {

    List<HandBook> selectAll();
    int findMaxId();
    HandBook selectById(int id);
    void updateHandBook(HandBook handBook);
    void deleteHandBook(int id);
    void insertHandBook(HandBook handBook);
}
