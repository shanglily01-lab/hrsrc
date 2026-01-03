package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.entity.*;
import com.pmp.hrsrc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ArchiveController {
    @Autowired
    UserService userService;

    @Autowired
    DistriService distriService;
    @Autowired
    ZpaccService zpaccService;
    @Autowired
    ProService proService;

    @Autowired
    HandBookService handBookService;




    @GetMapping("/index")
    public String index(HttpServletRequest req,Model model) {
        HttpSession session = req.getSession();
        User u = (User) session.getAttribute("user");
        if (null == u)
            return "redirect：login";
        List<Pro> pros = proService.selectProByUserId(u.getId());
        model.addAttribute("pros",pros);
        return "index";
    }
    @GetMapping("/users")
    public String getBook(Model model) {
        List<User> users = userService.selectAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/distri")
    public String getDistriList(Model model) {
        List<Distri> distris = distriService.selectAll();
        model.addAttribute("distris", distris);
        return "distris";
    }

    @GetMapping("/zpacc")
    public String getZpacc(Model model) {
        List<Zpacc> zps = zpaccService.selectAll();
        model.addAttribute("zps", zps);
        return "zpaccs";
    }

    @GetMapping("/handbooklist")
    public String getHandbookList(Model model){
        List<HandBook> handBooks = handBookService.selectAll();
        model.addAttribute("handbooks",handBooks);
        return "handbooklist";
    }
    @GetMapping("/handbook")
    public String getHandbook(Model model){
        List<HandBook> handBooks = handBookService.selectAll();
        model.addAttribute("handbooks",handBooks);
        return "handbook";
    }
}
