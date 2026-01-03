package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.VerAndReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VerAndReqMapper {

    List<VerAndReq> selectAllByVer(int vid);
    int insertVerAndReq(VerAndReq verAndReq);
    int deleteVerAndReq(int id);
}
