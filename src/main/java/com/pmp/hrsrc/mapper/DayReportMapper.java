package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.DayReport;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DayReportMapper {

    List<DayReport> selectAll(int pid);
    void updateDayReport(DayReport dayReport);
    void deleteDayReport(int id);
    DayReport selectById(int id);
    int findMaxId();
    void insertDayReport(DayReport dayReport);
}
