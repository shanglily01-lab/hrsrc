package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.RoleRight;
import com.pmp.hrsrc.entity.RoleRightDAO;
import com.pmp.hrsrc.mapper.RoleRightMapper;
import com.pmp.hrsrc.service.RoleRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleRightServiceImpl implements RoleRightService {

    @Autowired
    RoleRightMapper roleRightMapper;

    @Override
    public void insertRoleRight(RoleRight rr) {
        roleRightMapper.insertRoleRight(rr);
    }

    @Override
    public void deleteRoleRight(int id) {
        roleRightMapper.deleteRoleRight(id);
    }

    @Override
    public void updateRoleRight(RoleRight rr) {
        roleRightMapper.updateRoleRight(rr);
    }

    @Override
    public List<RoleRight> selectByUid(int id) {
        return roleRightMapper.selectByUid(id);
    }

    @Override
    public int selectMaxId() {
        return roleRightMapper.selectMaxId();
    }

    @Override
    public List<RoleRight> selectAll() {
        return roleRightMapper.selectAll();
    }

    @Override
    public List<RoleRightDAO> selectAllDAO() {
        return roleRightMapper.selectAllDAO();
    }
}
