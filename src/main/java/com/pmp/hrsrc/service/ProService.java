package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.Pro;

import java.util.List;

public interface ProService {

    List<Pro> selectAll();
    void updatePro(Pro pro);
    void deletePro(int id);
    Pro selectById(int id);
    int findMaxId();
    void insertPro(Pro pro);
    List<Pro> selectProByUserId(int id);
}
