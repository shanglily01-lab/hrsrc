package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.Plan;
import com.pmp.hrsrc.entity.PlanDAO;
import com.pmp.hrsrc.entity.PlanDetail;

import java.util.List;

public interface PlanService {

    List<Plan> selectAll(int id);
    void updatePlan(Plan plan);
    void deletePlan(int id);
    Plan selectById(int id);
    int findMaxId();
    void insertPlan(Plan plan);
    List<PlanDAO> selectAllDAO(int id);
    List<PlanDetail> selectPlanDetail(int id);
    void updatePlanDetail(PlanDetail planDetail);
    void deletePlanDetail(int id);
}
