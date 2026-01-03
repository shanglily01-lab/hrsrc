package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.Pro;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProMapper {

    List<Pro> selectAll();
    void updatePro(Pro pro);
    void deletePro(int id);
    Pro selectById(int id);
    int findMaxId();
    void insertPro(Pro pro);
    List<Pro> selectProByUserId(int id);
}
