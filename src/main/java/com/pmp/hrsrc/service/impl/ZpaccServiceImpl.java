package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.Zpacc;
import com.pmp.hrsrc.mapper.ZpaccMapper;
import com.pmp.hrsrc.service.ZpaccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZpaccServiceImpl implements ZpaccService {

    @Autowired
    ZpaccMapper zpaccMapper;

    @Override
    public List<Zpacc> selectAll() {
        return zpaccMapper.selectAll();
    }

    @Override
    public int insertZpacc(Zpacc zpacc) {
        zpaccMapper.insertZpacc(zpacc);
        return zpaccMapper.findMaxId();
    }

    @Override
    public int findMaxId() {
        return zpaccMapper.findMaxId();
    }

    @Override
    public Zpacc selectById(int id) {
        return zpaccMapper.selectById(id);
    }

    @Override
    public int updateZpacc(Zpacc zpacc) {
        zpaccMapper.updateZpacc(zpacc);
        return zpaccMapper.findMaxId();
    }

    @Override
    public int deleteZpacc(int id) {
        zpaccMapper.deleteZpacc(id);
        return zpaccMapper.findMaxId();
    }
}
