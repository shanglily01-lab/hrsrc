package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.UserCommnet;
import com.pmp.hrsrc.mapper.UserCommMapper;
import com.pmp.hrsrc.service.UserCommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCommServiceImpl implements UserCommService {

    @Autowired
    UserCommMapper userCommMapper;

    @Override
    public List<UserCommnet> selectAll() {
        return userCommMapper.selectAll();
    }

    @Override
    public void insertUserComm(UserCommnet userCommnet) {
        userCommMapper.insertUserComm(userCommnet);
    }

    @Override
    public void deleteUserComm(int id) {
        userCommMapper.deleteUserComm(id);
    }
}
