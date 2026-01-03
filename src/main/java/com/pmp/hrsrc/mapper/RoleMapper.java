package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    void insertRole(Role u);
    void deleteRole(int id);
    void updateRole(Role u);
    int findMaxId();
    List<Role> selectAll();
}
