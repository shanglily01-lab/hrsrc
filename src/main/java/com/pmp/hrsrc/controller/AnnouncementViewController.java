package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.entity.Announcement;
import com.pmp.hrsrc.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/announcement")
public class AnnouncementViewController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("announcements", announcementService.findAll());
        return "announcement/list";
    }

    @GetMapping("/edit")
    public String create(Model model) {
        model.addAttribute("announcement", null);
        return "announcement/edit";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Announcement announcement = announcementService.findById(id);
        if (announcement == null) {
            return "redirect:/announcement/list";
        }
        model.addAttribute("announcement", announcement);
        return "announcement/edit";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Announcement announcement = announcementService.findById(id);
        if (announcement == null) {
            return "redirect:/announcement/list";
        }
        model.addAttribute("announcement", announcement);
        return "announcement/view";
    }
} 