package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.Version;
import com.pmp.hrsrc.entity.VersionDAO;
import com.pmp.hrsrc.mapper.VersionMapper;
import com.pmp.hrsrc.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    VersionMapper versionMapper;

    @Override
    public List<Version> selectAll() {
        return versionMapper.selectAll();
    }

    @Override
    public void updateVersion(Version version) {
        versionMapper.updateVersion(version);
    }

    @Override
    public void deleteVersion(int id) {
     versionMapper.deleteVersion(id);
    }

    @Override
    public Version selectById(int id) {
        return versionMapper.selectById(id);
    }

    @Override
    public int findMaxId() {
        return versionMapper.findMaxId();
    }

    @Override
    public void insertVersion(Version version) {
        versionMapper.insertVersion(version);
    }

    @Override
    public List<VersionDAO> selectAllDAO(int id) {
        return  versionMapper.selectAllDAO(id);
    }
}
