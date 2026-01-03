package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.Plan;
import com.pmp.hrsrc.entity.PlanDAO;
import com.pmp.hrsrc.entity.PlanDetail;
import com.pmp.hrsrc.mapper.PlanMapper;
import com.pmp.hrsrc.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PlanServiceImpl implements PlanService {

   @Autowired
    PlanMapper planMapper;
    @Override
    public List<Plan> selectAll(int id) {
        return planMapper.selectAll(id);
    }

    @Override
    public void updatePlan(Plan plan) {
        planMapper.updatePlan(plan);
    }

    @Override
    public void deletePlan(int id) {
        planMapper.deletePlan(id);
    }

    @Override
    public Plan selectById(int id) {
        return planMapper.selectById(id);
    }

    @Override
    public int findMaxId() {
        return planMapper.findMaxId();
    }

    @Override
    public void insertPlan(Plan plan) {
        planMapper.insertPlan(plan);
    }

    @Override
    public List<PlanDAO> selectAllDAO(int id) {
        return planMapper.selectAllDAO(id);
    }

    @Override
    public List<PlanDetail> selectPlanDetail(int id) {
        return planMapper.selectPlanDetail(id);
    }

    @Override
    public void updatePlanDetail(PlanDetail planDetail) {
        planMapper.updatePlanDetail(planDetail);
    }

    @Override
    public void deletePlanDetail(int id) {
        planMapper.deletePlanDetail(id);
    }
}
