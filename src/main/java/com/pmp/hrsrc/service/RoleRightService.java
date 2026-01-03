package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.RoleRight;
import com.pmp.hrsrc.entity.RoleRightDAO;
import com.pmp.hrsrc.entity.UserRole;

import java.util.List;

public interface RoleRightService {

    void insertRoleRight(RoleRight rr);
    void deleteRoleRight(int id);
    void updateRoleRight(RoleRight rr);
    List<RoleRight> selectByUid(int id);
    int selectMaxId();
    List<RoleRight> selectAll();
    List<RoleRightDAO> selectAllDAO();
}
