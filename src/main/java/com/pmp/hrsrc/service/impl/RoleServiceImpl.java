package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.Role;
import com.pmp.hrsrc.entity.UserRole;
import com.pmp.hrsrc.mapper.RoleMapper;
import com.pmp.hrsrc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public void insertRole(Role u) {
        roleMapper.insertRole(u);

    }

    @Override
    public void deleteRole(int id) {
        roleMapper.deleteRole(id);
    }

    @Override
    public void updateRole(Role u) {
        roleMapper.updateRole(u);
    }

    @Override
    public int findMaxId() {
        return roleMapper.findMaxId();
    }

    @Override
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }
}
