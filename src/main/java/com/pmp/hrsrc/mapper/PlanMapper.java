package com.pmp.hrsrc.mapper;


import com.pmp.hrsrc.entity.Plan;
import com.pmp.hrsrc.entity.PlanDAO;
import com.pmp.hrsrc.entity.PlanDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanMapper {

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
