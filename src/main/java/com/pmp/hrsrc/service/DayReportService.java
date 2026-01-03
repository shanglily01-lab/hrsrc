package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.DayReport;

import java.util.List;

public interface DayReportService {

    List<DayReport> selectAll(int pid);
    void updateDayReport(DayReport dayReport);
    void deleteDayReport(int id);
    DayReport selectById(int id);
    int findMaxId();
    void insertDayReport(DayReport dayReport);
}
