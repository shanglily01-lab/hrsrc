package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.UserRole;
import com.pmp.hrsrc.entity.UserRoleDAO;
import com.pmp.hrsrc.mapper.UserRoleMapper;
import com.pmp.hrsrc.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public void insertUserRole(UserRole u) {
        userRoleMapper.insertUserRole(u);

    }

    @Override
    public void deleteUserRole(int id) {
        userRoleMapper.deleteUserRole(id);

    }

    @Override
    public void updateUserRole(UserRole u) {
        userRoleMapper.updateUserRole(u);
    }

    @Override
    public List<UserRole> selectByUid(int uid) {
        return userRoleMapper.selectByUid(uid);
    }

    @Override
    public int findMaxId() {
        return userRoleMapper.findMaxId();
    }

    @Override
    public List<UserRole> selectAll() {
        return userRoleMapper.selectAll();
    }

    @Override
    public List<UserRoleDAO> selectAllDAO() {
        return userRoleMapper.selectAllDAO();
    }
}
