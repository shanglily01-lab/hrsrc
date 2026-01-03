package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.User;
import com.pmp.hrsrc.mapper.UserMapper;
import com.pmp.hrsrc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertUser(User u) {
      userMapper.insertUser(u);
    }

    @Override
    public void deleteUser(int id) {
        userMapper.deleteUser(id);
    }

    @Override
    public void updateUser(User u) {
        userMapper.updateUser(u);
    }

    @Override
    public User validateUser(User user) {
        return userMapper.validateUser(user);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public int selectMaxId() {
        return userMapper.selectMaxId();
    }

    @Override
    public void updateDateAndIp(User user) {
        userMapper.updateDateAndIp(user);
    }

    @Override
    public User selectById(int id) {
        return userMapper.selectById(id);
    }
}
