package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.User;
import java.util.List;

public interface UserService {
    void insertUser(User u);
    void deleteUser(int id);
    void updateUser(User u);
    User validateUser(User user);
    List<User> selectAll();
    int selectMaxId();
    User selectById(int id);
    void updateDateAndIp(User user);
   
}
