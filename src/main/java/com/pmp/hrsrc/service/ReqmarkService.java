package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.Reqmark;

import java.util.List;

public interface ReqmarkService {

    List<Reqmark> selectAll(int id);
    void updateReqmark(Reqmark reqmark);
    void deleteReqmark(int id);
    Reqmark selectById(int id);
    int findMaxId();
    void insertReqmark(Reqmark reqmark);
}
