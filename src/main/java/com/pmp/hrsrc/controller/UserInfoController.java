package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.entity.*;
import com.pmp.hrsrc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserInfoController {

    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    RoleRightService roleRightService;
    @Autowired
    ProService proService;
    @Autowired
    RightService rightService;

    @Autowired
    ProfileService profileService;

    @Autowired
    HintCardService hintCardService;



    @GetMapping("/userroles")
    public String getUserRole(Model model){
        List<User> urs  = userService.selectAll();
        List<Role> roles = roleService.selectAll();
        model.addAttribute("users",urs);
        model.addAttribute("roles",roles);
        List<UserRoleDAO> urs2 = userRoleService.selectAllDAO();
        model.addAttribute("urs",urs2);
        return "userroles";
    }

    @GetMapping("/rolerights")
    public String getRoleRight(Model model)
    {
        List<Role> roles = roleService.selectAll();
        model.addAttribute("roles",roles);
        List<RoleRightDAO> rrs = roleRightService.selectAllDAO();
        model.addAttribute("rrs",rrs);
        List<Right> rights = rightService.selectAll();
        model.addAttribute("rights",rights);
        return "rolerights";
    }
    @GetMapping("/role")
    public String getRoleList(Model model)
    {
        List<Role> roles = roleService.selectAll();
        model.addAttribute("roles",roles);
        return "roles";
    }

    @GetMapping("/right")
    public String getRightList(Model model){
        List<Right> rights = rightService.selectAll();

        List<Pro> pros = proService.selectAll();
        model.addAttribute("rights",rights);
        model.addAttribute("pros",pros);
        return "rights";
    }
    @GetMapping("/profile")
    public String getProfile(HttpServletRequest req,Model model){
        HttpSession session =req.getSession();
        User u = (User) session.getAttribute("user");
        if(u==null ) return "login";
      //  System.out.println("uid="+u.getId()+",uname="+u.getUname());
        Profile profile = profileService.selectByUid(u.getId());
        if(profile ==null) {
            profile = new Profile();
            profile.setUid(u.getId());
            profile.setUname(u.getUname());
            profile.setId(0);
        }
        Secrete secrete = profileService.selectScode(u.getId());
        model.addAttribute("Profile",profile);
        model.addAttribute("Scode",secrete);
        return "profile";
    }

    @GetMapping("/hintCard")
    public String getHintCard(HttpServletRequest req,Model model){
        HttpSession session = req.getSession();
        User user =(User) session.getAttribute("user");
        if(null == user)
            return "redirect:login";
        List<HintCard> cards = hintCardService.selectById(user.getId());
        model.addAttribute("Cards",cards);
        model.addAttribute("User",user);
        return "hintcard";
    }


    @GetMapping("/usersecs")
    public String getUserSec(HttpServletRequest req,Model model){
        HttpSession session =req.getSession();
        User user =(User) session.getAttribute("user");
        if(null == user)
            return "redirect:login";
        List<Secrete> secs = profileService.selectAllScode();
        model.addAttribute("secs",secs);
        return "usersecs";

    }
    @GetMapping("/hintCardList")
    public String getHintCardList(Model model){
      
        List<HintCard> cards = hintCardService.selectAll();
        model.addAttribute("Cards",cards);
    
        return "hintcards";
    }
}
