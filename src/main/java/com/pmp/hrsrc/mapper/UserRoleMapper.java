package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.UserRole;
import com.pmp.hrsrc.entity.UserRoleDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    void insertUserRole(UserRole u);
    void deleteUserRole(int id);
    void updateUserRole(UserRole u);
    List<UserRole> selectByUid(int uid);
    int findMaxId();
    List<UserRole> selectAll();
    List<UserRoleDAO>selectAllDAO();
}
