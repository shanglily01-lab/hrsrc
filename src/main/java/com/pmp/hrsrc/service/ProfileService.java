package com.pmp.hrsrc.service;


import com.pmp.hrsrc.entity.Profile;
import com.pmp.hrsrc.entity.Secrete;

import java.util.List;

public interface ProfileService {

    List<Profile> selectAll();
    void updateProfile(Profile profile);
    void deleteProfile(int id);
    Profile selectByUid(int uid);
    int findMaxId();
    Secrete selectScode(int uid);
    void insertProfile(Profile profile);
    List<Secrete> selectAllScode();
}
