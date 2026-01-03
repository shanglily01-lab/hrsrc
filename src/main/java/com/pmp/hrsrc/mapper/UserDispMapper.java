package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.UserCommnet;
import com.pmp.hrsrc.entity.UserDisp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDispMapper {
    List<UserDisp> selectAll();
    void insertUserDisp(UserDisp userDisp);
    void updateUserDisp(UserDisp userDisp);
    void deleteUserDisp(int id);
    List<UserCommnet> findUserComm(int id);
}
