package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.Version;
import com.pmp.hrsrc.entity.VersionDAO;

import java.util.List;

public interface VersionService {

    List<Version> selectAll();
    void updateVersion(Version version);
    void deleteVersion(int id);
    Version selectById(int id);
    int findMaxId();
    void insertVersion(Version version);
    List<VersionDAO> selectAllDAO(int id);
}
