package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.Distri;

import java.util.List;

public interface DistriService {

    List<Distri> selectAll();
    void updateDistri(Distri distri) ;
    void deleteDistri(int id);
    Distri selectById(int id);
    int findMaxId();
    void insertDistri(Distri distri);
}
