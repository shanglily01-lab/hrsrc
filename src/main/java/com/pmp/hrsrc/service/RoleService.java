package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.Role;


import java.util.List;

public interface RoleService {
    void insertRole(Role u);
    void deleteRole(int id);
    void updateRole(Role u);
    int findMaxId();
    List<Role> selectAll();
}
