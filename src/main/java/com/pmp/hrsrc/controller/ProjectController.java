package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.entity.*;
import com.pmp.hrsrc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    ProService proService;
    @Autowired
    PlanService planService;
    @Autowired
    VersionService versionService;
    @Autowired
    UserService userService;
    @Autowired
    WeekReportService weekReportService;
    @Autowired
    MeetReportService meetReportService;
    @Autowired
    RequireService requireService;
    @Autowired
    ReqmarkService reqmarkService;
    @Autowired
    DemarkService demarkService;
    @Autowired
    DayReportService dayReportService;

    @GetMapping("/pro")
    public String getPros(Model model)
    {
        List<Pro> pros = proService.selectAll();
        model.addAttribute("projectList",pros);
        return "pros";
    }

    @GetMapping("/plan")
    public String getPlans(int pid, Model model)
    {
        Pro pro = proService.selectById(pid);
        List<User> urs =userService.selectAll();
        List<VersionDAO> vers  = versionService.selectAllDAO(pid);
        List<PlanDAO> plans = planService.selectAllDAO(pid);
        model.addAttribute("plans",plans);
        model.addAttribute("vers",vers);
        model.addAttribute("pro",pro);
        model.addAttribute("urs",urs);
        return "plans";
    }
    @GetMapping("/version")
    public String getVers(int pid ,Model model)
    {
        List<VersionDAO> vers = versionService.selectAllDAO(pid);
        model.addAttribute("vers",vers);
        Pro pro = proService.selectById(pid);
        List<Require> reqs= requireService.selectAll(pid);
        List<Reqmark> marks = reqmarkService.selectAll(pid);
        model.addAttribute("reqs",reqs);
        model.addAttribute("marks",marks);
        model.addAttribute("pro",pro);
       return "versions";
    }
    @GetMapping("/dayreport")
    public String getDayReport(int pid,Model model)
    {
        List<DayReport> dayReports =  dayReportService.selectAll(pid);
        model.addAttribute("dayReports",dayReports);
        model.addAttribute("pid",pid);
        return "dayreports";

    }
    @GetMapping("/weekreport")
    public String weekreport(int pid, Model model) {
        List<WeekReport> weeks=weekReportService.selectAll(pid);
        List<User> urs =userService.selectAll();
        model.addAttribute("pid",pid);
        model.addAttribute("weeks", weeks);
        return "weekreports";
    }
    @GetMapping("/meetreport")
    public String getMeetReport( int pid,Model model) {

        List<MeetReport> mrs = meetReportService.selectAll(pid);
        model.addAttribute("mrs", mrs);
        model.addAttribute("pid",pid);
        return "meetreports";

    }
    @GetMapping("/reqmark")
    public String getReqmarkList(int pid,Model model) {
        List<User> users = userService.selectAll();
        model.addAttribute("urs", users);
        model.addAttribute("pid",pid);
        List<Reqmark> reqmarks = reqmarkService.selectAll(pid);
        model.addAttribute("reqmarks", reqmarks);
        return "reqmarks";

    }
    @GetMapping("/req")
    public String getColumn(int pid, Model model) {

        List<User> users = userService.selectAll();
        List<Require> reqs = new ArrayList<>();
        reqs = requireService.selectAll(pid);
        model.addAttribute("reqs", reqs);
        model.addAttribute("pid", pid);
        model.addAttribute("users", users);
        return "reqs";
    }
    @GetMapping("/demark")
    public String getDemarkList(Model model) {
        List<Demark> demarks = demarkService.selectAll();
        model.addAttribute("demarks", demarks);
        return "demarks";
    }
    @GetMapping("/pass")
    public String modifyPass(){
        return "pass";
    }

}
