package com.pmp.hrsrc.mapper;


import com.pmp.hrsrc.entity.Distri;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DistriMapper {

    List<Distri> selectAll();
    void updateDistri(Distri distri);
    void deleteDistri(int id);
    Distri selectById(int id);
    int findMaxId();
    void insertDistri(Distri distri);

}
