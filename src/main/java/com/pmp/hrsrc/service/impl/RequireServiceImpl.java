package com.pmp.hrsrc.service.impl;


import com.pmp.hrsrc.entity.Require;
import com.pmp.hrsrc.mapper.RequireMapper;
import com.pmp.hrsrc.service.RequireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequireServiceImpl implements RequireService {

    @Autowired
    RequireMapper requireMapper;

    @Override
    public List<Require> selectAll(int id) {
        return requireMapper.selectAll(id);
    }

    @Override
    public void insertRequire(Require req) {
        requireMapper.insertRequire(req);
    }

    @Override
    public void updateRequire(Require req) {
         requireMapper.updateRequire(req);
    }
    @Override
    public void deleteRequire(int id) {
        requireMapper.deleteRequire(id);
    }

    @Override
    public Require findById(int rid) {
        return requireMapper.findById(rid);
    }
}
