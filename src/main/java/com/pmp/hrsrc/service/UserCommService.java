package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.UserCommnet;

import java.util.List;

public interface UserCommService {
    List<UserCommnet> selectAll();
    void insertUserComm(UserCommnet userCommnet);
    void deleteUserComm(int id);
}
