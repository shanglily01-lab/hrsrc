package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.Right;
import com.pmp.hrsrc.mapper.RightMapper;
import com.pmp.hrsrc.service.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RightServiceImpl  implements RightService {

    @Autowired
    RightMapper rightMapper;

    @Override
    public void insertRight(Right r) {
        rightMapper.insertRight(r);
    }

    @Override
    public void deleteRight(int id) {
        rightMapper.deleteRight(id);
    }

    @Override
    public void updateRight(Right r) {
        rightMapper.updateRight(r);
    }

    @Override
    public int findMaxId() {
        return rightMapper.findMaxId();
    }

    @Override
    public List<Right> selectAll() {
        return rightMapper.selectAll();
    }
}
