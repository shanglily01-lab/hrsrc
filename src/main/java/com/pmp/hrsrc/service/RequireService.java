package com.pmp.hrsrc.service;
import com.pmp.hrsrc.entity.Require;
import java.util.List;
public interface RequireService {
    List<Require> selectAll(int id);
    void insertRequire(Require req);
    void deleteRequire(int id);
    void updateRequire(Require req);
    Require findById(int rid);
}
