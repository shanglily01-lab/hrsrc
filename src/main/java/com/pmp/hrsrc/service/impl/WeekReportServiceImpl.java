package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.PidAndUid;
import com.pmp.hrsrc.entity.WeekReport;
import com.pmp.hrsrc.mapper.WeekReportMapper;
import com.pmp.hrsrc.service.WeekReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeekReportServiceImpl implements WeekReportService {


    @Autowired
    WeekReportMapper weekReportMapper;
    @Override
    public List<WeekReport> selectAll(int id) {
        return weekReportMapper.selectAll(id);
    }

    @Override
    public void insertWeekReport(WeekReport wk) {
        weekReportMapper.insertWeekReport(wk);
    }

    @Override
    public void deleteWeekReport(int zid) {
        weekReportMapper.deleteWeekReport(zid);
    }

    @Override
    public void updateWeekReport(WeekReport wk) {
        weekReportMapper.updateWeekReport(wk);
    }

    @Override
    public WeekReport selectById(int id) {
        return weekReportMapper.selectById(id);
    }

    @Override
    public int findMaxId() {
        return weekReportMapper.findMaxId();
    }
}
