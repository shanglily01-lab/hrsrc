package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.Demark;

import java.util.List;

public interface DemarkService {

    List<Demark> selectAll();
    void updateDemark(Demark demark);
    void deleteDemark(int id);
    Demark selectById(int id);
    int findMaxId();
    void insertDemark(Demark demark);
}
