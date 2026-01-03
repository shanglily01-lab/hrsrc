package com.pmp.hrsrc.service.impl;


import com.pmp.hrsrc.entity.Profile;
import com.pmp.hrsrc.entity.Secrete;
import com.pmp.hrsrc.mapper.ProfileMapper;
import com.pmp.hrsrc.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileMapper profileMapper;

    @Override
    public List<Profile> selectAll() {
        return profileMapper.selectAll();
    }

    @Override
    public void updateProfile(Profile profile) {
        profileMapper.updateProfile(profile);
    }

    @Override
    public void deleteProfile(int id) {
        profileMapper.deleteProfile(id);
    }

    @Override
    public Profile selectByUid(int uid) {
        return profileMapper.selectByUid(uid);
    }

    @Override
    public int findMaxId() {
        return profileMapper.findMaxId();
    }

    @Override
    public Secrete selectScode(int uid) {
        return profileMapper.selectScode(uid);
    }

    @Override
    public void insertProfile(Profile profile) {
        profileMapper.insertProfile(profile);
    }

    @Override
    public List<Secrete> selectAllScode() {
        return profileMapper.selectAllScode();
    }
}
