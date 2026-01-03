package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.UserCommnet;
import com.pmp.hrsrc.entity.UserDisp;

import java.util.List;

public interface UserDispService {
    List<UserDisp> selectAll();
    void insertUserDisp(UserDisp userDisp);
    void updateUserDisp(UserDisp userDisp);
    void deleteUserDisp(int id);

    List<UserCommnet> findUserComm(int id);
}
