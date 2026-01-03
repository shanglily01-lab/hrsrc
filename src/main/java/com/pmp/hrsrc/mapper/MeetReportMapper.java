package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.MeetReport;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetReportMapper {

    List<MeetReport> selectAll(int id);
    int insertMeetreport(MeetReport meetReport);
    int findMaxId();
    MeetReport selectById(int id);
    int updateMeetreport(MeetReport meetReport);
    int deleteMeetreport(int id);
}
