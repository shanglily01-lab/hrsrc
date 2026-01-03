package com.pmp.hrsrc.mapper;



import com.pmp.hrsrc.entity.Version;
import com.pmp.hrsrc.entity.VersionDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VersionMapper {

    List<Version> selectAll();
    void updateVersion(Version version);
    void deleteVersion(int id);
    Version selectById(int id);
    int findMaxId();
    void insertVersion(Version version);
    List<VersionDAO> selectAllDAO(int id);
}
