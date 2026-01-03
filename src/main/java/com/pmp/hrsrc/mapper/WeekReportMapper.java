package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.PidAndUid;
import com.pmp.hrsrc.entity.WeekReport;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WeekReportMapper {

     List<WeekReport> selectAll(int id);
     void insertWeekReport(WeekReport wk);
     void deleteWeekReport(int zid);
     void updateWeekReport(WeekReport wk);
     WeekReport selectById(int id);
     int findMaxId();

}
