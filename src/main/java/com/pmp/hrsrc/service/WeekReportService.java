package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.PidAndUid;
import com.pmp.hrsrc.entity.WeekReport;

import java.util.List;

public interface WeekReportService {

     List<WeekReport> selectAll(int pid);
     void insertWeekReport(WeekReport wk);
     void deleteWeekReport(int zid);
     void updateWeekReport(WeekReport wk);
     WeekReport selectById(int id);
     int findMaxId();
     

}
