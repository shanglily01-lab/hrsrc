package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.UserCommnet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCommMapper {

    List<UserCommnet> selectAll();
    void insertUserComm(UserCommnet userCommnet);
    void deleteUserComm(int id);
}
