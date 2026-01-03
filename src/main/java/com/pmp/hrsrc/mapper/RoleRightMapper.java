package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.RoleRight;
import com.pmp.hrsrc.entity.RoleRightDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleRightMapper {

    void insertRoleRight(RoleRight rr);
    void deleteRoleRight(int id);
    void updateRoleRight(RoleRight rr);
    List<RoleRight> selectByUid(int id);
    int selectMaxId();
    List<RoleRight> selectAll();
    List<RoleRightDAO> selectAllDAO();
}
