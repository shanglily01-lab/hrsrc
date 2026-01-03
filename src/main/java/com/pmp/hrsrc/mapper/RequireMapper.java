package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.Require;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RequireMapper {

    List<Require> selectAll(int id);
    void insertRequire(Require req);
    void deleteRequire(int id);
    void updateRequire(Require req);
    Require findById(int rid);
}
