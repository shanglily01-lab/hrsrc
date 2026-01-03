package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.Demark;
import com.pmp.hrsrc.mapper.DemarkMapper;
import com.pmp.hrsrc.service.DemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DemarkServiceImpl implements DemarkService {

    @Autowired
    DemarkMapper demarkMapper;

    @Override
    public List<Demark> selectAll() {
        return demarkMapper.selectAll();
    }

    @Override
    public void updateDemark(Demark demark) {
        demarkMapper.updateDemark(demark);
    }

    @Override
    public void deleteDemark(int id) {
        demarkMapper.deleteDemark(id);
    }

    @Override
    public Demark selectById(int id) {
        return demarkMapper.selectById(id);
    }

    @Override
    public int findMaxId() {
        return demarkMapper.findMaxId();
    }

    @Override
    public void insertDemark(Demark demark) {
        demarkMapper.insertDemark(demark);
    }
}
