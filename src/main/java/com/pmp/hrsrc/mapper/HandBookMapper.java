package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.HandBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HandBookMapper {

    List<HandBook> selectAll();
    int findMaxId();
    HandBook selectById(int id);
    void updateHandBook(HandBook handBook);
    void deleteHandBook(int id);
    void insertHandBook(HandBook handBook);
}
