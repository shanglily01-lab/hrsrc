package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.Right;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RightMapper {

    void insertRight(Right r);
    void deleteRight(int id);
    void updateRight(Right r);
    int findMaxId();
    List<Right> selectAll();
}
