package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.entity.*;
import com.pmp.hrsrc.service.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@Api(tags = "项目管理接口")
public class ProjectRestController {

    @Autowired
    ProService projectService;
    @Autowired
    PlanService planService;
    @Autowired
    VersionService versionService;

    @Autowired
    ReqmarkService reqmarkService;
    @Autowired
    VerAndReqService verAndReqService;
    @Autowired
    MeetReportService meetReportService;

    @Autowired
    DayReportService dayReportService;

    @Autowired
    WeekReportService weekReportService;

    @Autowired
    RequireService requireService;

    @GetMapping("/projects")
    public List<Pro> getAllProjects() {
        return projectService.selectAll();
    }

    @GetMapping("/project")
    public Pro getProject(int id) {
        return projectService.selectById(id);
    }

    @PostMapping("/insertProject")
    public int insertProject(@RequestBody Pro project) {
        project.setId(projectService.findMaxId());
        projectService.insertPro(project);
        return 1;
    }

    @PostMapping("/updateProject")
    public int updateProject(@RequestBody Pro project) {
        projectService.updatePro(project);
        return 1;
    }

    @PostMapping("/deleteProject")
    public int deleteProject(int id) {
        projectService.deletePro(id);
        return 1;
    }

    @PostMapping("/insertPlan")
    public int insertPlan(Plan dao){
        planService.insertPlan(dao);
        return 1;
    }
    @PostMapping("/deletePlan")
    public int deletePlan(int id)
    {
        planService.deletePlan(id);
        return 1;
    }

    @PostMapping("/updatePlan")
    public int updatePlan(Plan dao)
    {
        planService.updatePlan(dao);
        return 1;
    }

    @PostMapping("/insertVersion")
    public int insertVersion(@RequestBody Version dao){
        dao.setId(0);
        versionService.insertVersion(dao);
        return 1;
    }

    @PostMapping("/deleteVersion")
    public int deleteVersion(int id)
    {
        versionService.deleteVersion(id);
        return 1;
    }

    @PostMapping("/updateVersion")
    public int updateVersion(@RequestBody Version dao)
    {
        versionService.updateVersion(dao);
        return 1;
    }


    @PostMapping("/insertReqmark")
    public int insertReqmark(@RequestBody Reqmark reqmark)
    {
        reqmark.setId(reqmarkService.findMaxId());
        reqmarkService.insertReqmark(reqmark);
        return 1 ;
    }

    @PostMapping("/updateReqmark")
    public int updateReqmark(@RequestBody Reqmark reqmark)
    {
        reqmarkService.updateReqmark(reqmark);
        return 1;
    }

    @PostMapping("/deleteReqmark")
    public int deleteReqmark(int id)
    {
        reqmarkService.deleteReqmark(id);
        return reqmarkService.findMaxId();
    }



    @PostMapping("/insertMeetreport")
    public int insertMeetReport(@RequestBody MeetReport meetReport){
        meetReport.setId(meetReportService.findMaxId());
        return  meetReportService.insertMeetport(meetReport);
    }

    @PostMapping("/deleteMeetreport")
    public int deleteMeetReport( int id)
    {
        return meetReportService.deleteMeetreport(id);
    }

    @PostMapping("/updateMeetreport")
    public int updateMeetReport(@RequestBody MeetReport meetReport)
    {
        return meetReportService.updateMeetreport(meetReport);
    }

    @GetMapping("/getMeetreport")
    public MeetReport getMeetReport(int id) {
        return meetReportService.selectById(id);
    }



    @PostMapping("/deleteDayReport")
    public int deleteDayReport(int id)
    {
        dayReportService.deleteDayReport(id);
        return 1;
    }

    @PostMapping("/insertDayReport")
    public int insertDayReport(@RequestBody DayReport dayReport) {
        dayReport.setId(dayReportService.findMaxId());
        dayReportService.insertDayReport(dayReport);
        return 1;
    }

    @PostMapping("/updateDayReport")
    public int updateDayReport(@RequestBody DayReport dayReport) {
        dayReportService.updateDayReport(dayReport);
        return 1;
    }

    @GetMapping("/getDayReport")
    public DayReport getDayReport(int id) {
        return dayReportService.selectById(id);
    }

    @PostMapping("/deleteWeekReport")
    public int deleteWeekReport(int id) {
        weekReportService.deleteWeekReport(id);
        return 1;
    }

    @PostMapping("/insertWeekReport")
    public int insertWeekReport(@RequestBody WeekReport weekReport) {
        weekReport.setId(weekReportService.findMaxId());
        weekReportService.insertWeekReport(weekReport);
        return 1;
    }

    @PostMapping("/updateWeekReport")
    public int updateWeekReport(@RequestBody WeekReport weekReport) {
        weekReportService.updateWeekReport(weekReport);
        return 1;
    }

    @GetMapping("/getWeekReport")
    public WeekReport getWeekReport(Integer id) {
        return weekReportService.selectById(id);
    }

    @PostMapping("/deleteReq")
    public int deleteReq(Integer id) {
        requireService.deleteRequire(id);
        return 1;
    }

}
