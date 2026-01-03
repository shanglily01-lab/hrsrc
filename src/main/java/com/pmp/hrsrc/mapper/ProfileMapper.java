package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.DayReport;
import com.pmp.hrsrc.entity.Profile;
import com.pmp.hrsrc.entity.Secrete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProfileMapper {

    List<Profile> selectAll();
    void updateProfile(Profile profile);
    void deleteProfile(int id);
    Profile selectByUid(int uid);
    int findMaxId();
    Secrete selectScode(int uid);
    List<Secrete> selectAllScode();
    void insertProfile(Profile profile);
}
