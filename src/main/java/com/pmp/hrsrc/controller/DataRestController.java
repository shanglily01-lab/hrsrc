package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.entity.*;
import com.pmp.hrsrc.service.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(tags = "用户接口")
public class DataRestController {

    @Autowired
    UserService userService;
    @Autowired
    DistriService distriService;

    @Autowired
    UserDispService userDispService;

    @Autowired
    HandBookService handBookService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/updatePwd")
    public int updateUserPwd(@RequestParam("npd") String npd,HttpServletRequest request)  {
        if (npd == null || npd=="") return 0;
        User user = (User) ((HttpServletRequest) request).getSession().getAttribute("user");
        if (user != null) {
            user.setUpass(npd);
            user.encryptPassword(); // 加密新密码
            userService.updateUser(user);
            return 1;
        }
        return 0;
    }


    @PostMapping("/insertUser")
    public int insertUser(@RequestParam String uname, @RequestParam String upass) {
        User user = new User();
        user.setUname(uname);
        // 加密密码
        user.setUpass(passwordEncoder.encode(upass));
        user.setId(userService.selectMaxId());
        userService.insertUser(user);
        return 1;
    }

    @PostMapping("/deleteUser")
    public int deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
        return 1;
    }

    @PostMapping("/insertDistri")
    public int insertDistri(Distri distri) {

        if (null == distri) return 0;
        int id = distriService.findMaxId();
        distri.setId(id);
        distriService.insertDistri(distri);
        return id + 1;
    }

    @PostMapping("/updateDistri")
    public int updateDistri(Distri distri) {
        if (null == distri) return 0;
        distriService.updateDistri(distri);
        return 1;
    }

    @PostMapping("/deleteDistri")
    public int deleteDistri(int id) {
        distriService.deleteDistri(id);
        return 1;
    }

    @PostMapping("/insertHandBook")
    public int insertHandBook(HandBook handBook){
        handBook.setId(handBookService.findMaxId());
        handBookService.insertHandBook(handBook);
        return 1;
    }

    @PostMapping("/deleteHandBook")
    public int deleteHandBook(int id){
        handBookService.deleteHandBook(id);
        return 1;
    }
    @PostMapping("/updateHandBook")
    public int updateHandBook(HandBook handBook){
        handBookService.updateHandBook(handBook);
        return 1;
    }

    @GetMapping("/getHandBook")
    public HandBook getHandBook(int id){
        return handBookService.selectById(id);
    }
}
