package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.VerAndReq;
import com.pmp.hrsrc.mapper.VerAndReqMapper;
import com.pmp.hrsrc.service.VerAndReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerAndReqServiceImpl implements VerAndReqService {

    @Autowired
    VerAndReqMapper verAndReqMapper;
    @Override
    public List<VerAndReq> selectAllByVer(int vid) {
        return verAndReqMapper.selectAllByVer(vid);
    }

    @Override
    public int insertVerAndReq(VerAndReq verAndReq) {
        verAndReqMapper.insertVerAndReq(verAndReq);
        return 1;
    }

    @Override
    public int deleteVerAndReq(int id) {
        verAndReqMapper.deleteVerAndReq(id);
        return 1;
    }
}
