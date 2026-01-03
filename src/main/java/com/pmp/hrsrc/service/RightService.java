package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.Right;

import java.util.List;

public interface RightService {
    void insertRight(Right r);
    void deleteRight(int id);
    void updateRight(Right r);
    int findMaxId();
    List<Right> selectAll();
}
