package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.Reqmark;
import com.pmp.hrsrc.mapper.ReqmarkMapper;
import com.pmp.hrsrc.service.ReqmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReqmarkServiceImpl implements ReqmarkService {

    @Autowired
    ReqmarkMapper reqmarkMapper;

    @Override
    public List<Reqmark> selectAll(int id) {
        return reqmarkMapper.selectAll(id);
    }

    @Override
    public void updateReqmark(Reqmark reqmark) {
        reqmarkMapper.updateReqmark(reqmark);
    }

    @Override
    public void deleteReqmark(int id) {
        reqmarkMapper.deleteReqmark(id);

    }

    @Override
    public Reqmark selectById(int id) {
        return reqmarkMapper.selectById(id);
    }

    @Override
    public int findMaxId() {
        return reqmarkMapper.findMaxId();
    }

    @Override
    public void insertReqmark(Reqmark reqmark) {
        reqmarkMapper.insertReqmark(reqmark);
    }
}
