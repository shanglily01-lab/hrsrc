package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.Demark;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface DemarkMapper {

    List<Demark> selectAll();
    void updateDemark(Demark demark);
    void deleteDemark(int id);
    Demark selectById(int id);
    int findMaxId();
    void insertDemark(Demark demark);
}
