package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.entity.LeaveApplication;
import com.pmp.hrsrc.service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/leave")
public class LeaveApplicationViewController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @GetMapping("/my")
    public String myApplications(Model model, RedirectAttributes redirectAttributes) {
        try {
            List<LeaveApplication> applications = leaveApplicationService.findByDepartment("技术部");
            model.addAttribute("applications", applications);
            return "leave/my";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("applications", Collections.emptyList());
            return "leave/my";
        }
    }

    @GetMapping("/approve")
    public String approve(Model model) {
        try {
            model.addAttribute("applications", leaveApplicationService.findPendingByApproverId(1L));
            return "leave/approve";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("applications", Collections.emptyList());
            return "leave/approve";
        }
    }
} 