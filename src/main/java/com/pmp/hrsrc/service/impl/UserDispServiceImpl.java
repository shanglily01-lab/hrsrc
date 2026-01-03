package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.UserCommnet;
import com.pmp.hrsrc.entity.UserDisp;
import com.pmp.hrsrc.mapper.UserDispMapper;
import com.pmp.hrsrc.service.UserDispService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDispServiceImpl implements UserDispService {

    @Autowired
    UserDispMapper userDispMapper;

    @Override
    public List<UserDisp> selectAll() {
        return userDispMapper.selectAll();
    }

    @Override
    public void insertUserDisp(UserDisp userDisp) {
        userDispMapper.insertUserDisp(userDisp);
    }

    @Override
    public void updateUserDisp(UserDisp userDisp) {
        userDispMapper.updateUserDisp(userDisp);
    }

    @Override
    public void deleteUserDisp(int id) {
        userDispMapper.deleteUserDisp(id);
    }

    @Override
    public List<UserCommnet> findUserComm(int id) {
        return userDispMapper.findUserComm(id);
    }
}
