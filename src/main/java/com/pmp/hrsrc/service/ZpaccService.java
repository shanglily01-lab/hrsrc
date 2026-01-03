package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.Zpacc;

import java.util.List;

public interface ZpaccService {

    List<Zpacc> selectAll();
    int insertZpacc(Zpacc zpacc);
    int findMaxId();
    Zpacc selectById(int id);
    int updateZpacc(Zpacc zpacc);
    int  deleteZpacc(int id);
}
