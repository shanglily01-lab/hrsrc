package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.MeetReport;
import com.pmp.hrsrc.mapper.MeetReportMapper;
import com.pmp.hrsrc.service.MeetReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MeetReportServiceImpl  implements MeetReportService {


    @Autowired
    MeetReportMapper meetReportMapper;
    @Override
    public List<MeetReport> selectAll(int id) {
        return  meetReportMapper.selectAll(id);
    }

    @Override
    public int insertMeetport(MeetReport meetReport) {
        meetReportMapper.insertMeetreport(meetReport);
        return meetReportMapper.findMaxId();
    }

    @Override
    public int findMaxId() {
        return meetReportMapper.findMaxId();
    }

    @Override
    public MeetReport selectById(int id) {
        return meetReportMapper.selectById(id);
    }

    @Override
    public int updateMeetreport(MeetReport meetReport) {
        meetReportMapper.updateMeetreport(meetReport);
        return findMaxId();
    }

    @Override
    public int deleteMeetreport(int id) {
        meetReportMapper.deleteMeetreport(id);
        return findMaxId();
    }
}
