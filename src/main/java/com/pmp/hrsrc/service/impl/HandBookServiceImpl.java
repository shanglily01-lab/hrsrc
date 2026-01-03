package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.HandBook;
import com.pmp.hrsrc.mapper.HandBookMapper;
import com.pmp.hrsrc.service.HandBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandBookServiceImpl implements HandBookService {

    @Autowired
    HandBookMapper handBookMapper;

    @Override
    public List<HandBook> selectAll() {
        return handBookMapper.selectAll();
    }

    @Override
    public int findMaxId() {
        return handBookMapper.findMaxId();
    }

    @Override
    public HandBook selectById(int id) {
        return handBookMapper.selectById(id);
    }

    @Override
    public void updateHandBook(HandBook handBook) {
        handBookMapper.updateHandBook(handBook);
    }

    @Override
    public void deleteHandBook(int id) {
        handBookMapper.deleteHandBook(id);
    }

    @Override
    public void insertHandBook(HandBook handBook) {
        handBookMapper.insertHandBook(handBook);
    }
}
