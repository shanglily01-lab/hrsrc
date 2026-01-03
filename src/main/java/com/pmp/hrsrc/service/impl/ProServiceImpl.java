package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.Pro;
import com.pmp.hrsrc.mapper.ProMapper;
import com.pmp.hrsrc.service.ProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProServiceImpl implements ProService {

    @Autowired
    ProMapper proMapper;

    @Override
    public List<Pro> selectAll() {
        return proMapper.selectAll();
    }

    @Override
    public void updatePro(Pro pro) {
        proMapper.updatePro(pro);

    }

    @Override
    public void deletePro(int id) {
        proMapper.deletePro(id);
    }

    @Override
    public Pro selectById(int id) {
        return proMapper.selectById(id);
    }

    @Override
    public int findMaxId() {
        return proMapper.findMaxId();
    }

    @Override
    public void insertPro(Pro pro) {
        proMapper.insertPro(pro);
    }

    @Override
    public List<Pro> selectProByUserId(int id) {
        return proMapper.selectProByUserId(id);
    }
}
