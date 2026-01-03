package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.Distri;
import com.pmp.hrsrc.mapper.DistriMapper;
import com.pmp.hrsrc.service.DistriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DistriServiceImpl implements DistriService {

    @Autowired
    DistriMapper distriMapper;

    @Override
    public List<Distri> selectAll() {
        return distriMapper.selectAll();
    }

    @Override
    public void updateDistri(Distri distri) {
        distriMapper.updateDistri(distri);
    }

    @Override
    public void deleteDistri(int id) {
        distriMapper.deleteDistri(id);
    }

    @Override
    public Distri selectById(int id) {
        return distriMapper.selectById(id);
    }

    @Override
    public int findMaxId() {
        return distriMapper.findMaxId();
    }

    @Override
    public void insertDistri(Distri distri) {
        distriMapper.insertDistri(distri);
    }
}
