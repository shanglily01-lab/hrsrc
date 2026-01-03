package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.UserRole;
import com.pmp.hrsrc.entity.UserRoleDAO;

import java.util.List;

public interface UserRoleService {

    void insertUserRole(UserRole u);
    void deleteUserRole(int id);
    void updateUserRole(UserRole u);
    List<UserRole> selectByUid(int uid);
    int findMaxId();
    List<UserRole> selectAll();
    List<UserRoleDAO>selectAllDAO();
}
