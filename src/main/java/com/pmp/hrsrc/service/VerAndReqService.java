package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.VerAndReq;

import java.util.List;

public interface VerAndReqService {
    List<VerAndReq> selectAllByVer(int vid);
    int insertVerAndReq(VerAndReq verAndReq);
    int deleteVerAndReq(int id);
}
