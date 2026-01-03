package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    void insertUser(User u);
    void deleteUser(int id);
    void updateUser(User u);
    User validateUser(User user);
    List<User> selectAll();
    User selectById(int id);
    int selectMaxId();
    void updateDateAndIp(User user);

    @Select("SELECT * FROM user WHERE uname = #{uname}")
    User selectByUname(String uname);
}
