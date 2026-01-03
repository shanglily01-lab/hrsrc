package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.MeetReport;

import java.util.List;


public interface MeetReportService {

    List<MeetReport> selectAll(int id);
    int insertMeetport(MeetReport meetReport);
    int findMaxId();
    MeetReport selectById(int id);
    int updateMeetreport(MeetReport meetReport);
    int deleteMeetreport(int id);
}
