package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.DayReport;
import com.pmp.hrsrc.mapper.DayReportMapper;
import com.pmp.hrsrc.service.DayReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayReportServiceImpl implements DayReportService {

    @Autowired
    DayReportMapper dayReportMapper;

    @Override
    public List<DayReport> selectAll(int pid) {
        return dayReportMapper.selectAll(pid);
    }

    @Override
    public void updateDayReport(DayReport dayReport) {
        dayReportMapper.updateDayReport(dayReport);
    }

    @Override
    public void deleteDayReport(int id) {
        dayReportMapper.deleteDayReport(id);
    }

    @Override
    public DayReport selectById(int id) {
        return dayReportMapper.selectById(id);
    }

    @Override
    public int findMaxId() {
        return dayReportMapper.findMaxId();
    }

    @Override
    public void insertDayReport(DayReport dayReport) {
        dayReportMapper.insertDayReport(dayReport);
    }
}
