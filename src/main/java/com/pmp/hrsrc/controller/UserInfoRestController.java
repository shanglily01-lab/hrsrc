package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.entity.*;
import com.pmp.hrsrc.service.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/api")
@Api(tags = "用户权限接口")
@RestController
public class UserInfoRestController {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoRestController.class);

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        logger.info("文件上传路径: {}", uploadPath);
        logger.info("当前工作目录: {}", System.getProperty("user.dir"));
    }

    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleService roleService;
    @Autowired
    RoleRightService roleRightService;
    @Autowired
    RightService rightService;
    @Autowired
    ProfileService profileService;
    @Autowired
    HintCardService hintCardService;

    @PostMapping("/insertUserRole")
    public int insertUserRole(@RequestBody UserRole ur) {
        ur.setId(userRoleService.findMaxId());
        userRoleService.insertUserRole(ur);
        return 1;
    }
    @PostMapping("/updateUserRole")
    public int updateUserRole(UserRole dao){
        userRoleService.updateUserRole(dao);
        return 1;
    }
    @PostMapping("/deleteUserRole")
    public int deleteUserRole(int id)
    {
        userRoleService.deleteUserRole(id);
        return 1;
    }
    @PostMapping("/insertRoleRight")
    public int insertRoleRight(RoleRight dao)
    {
        dao.setId(roleRightService.selectMaxId());
        roleRightService.insertRoleRight(dao);
        return 1;
    }
    @PostMapping("/updateRoleRight")
    public int updateRoleRight(RoleRight dao)
    {
        roleRightService.updateRoleRight(dao);
        return 1;
    }
    @PostMapping("/deleteRoleRight")
    public int deleteRoleRight(int id){
        roleRightService.deleteRoleRight(id);
        return 1;
    }

    @PostMapping("/insertRole")
    public int insertRole(Role role){
        role.setId(roleService.findMaxId());
        roleService.insertRole(role);
        return 1;
    }
    @PostMapping("/updateRole")
    public int updateRole(Role role)
    {
        roleService.updateRole(role);
        return 1;
    }
    @PostMapping("/deleteRole")
    public int deleteRole(int id)
    {
        roleService.deleteRole(id);
        return 1;
    }

    @PostMapping("/insertRight")
    public int insertRight(Right r)
    {
        r.setId(rightService.findMaxId());
        rightService.insertRight(r);
        return 1;
    }
    @PostMapping("/updateRight")
    public int updateRight(Right r)
    {
        rightService.updateRight(r);
        return 1;
    }
    @PostMapping("/deleteRight")
    public int deleteRight(int id)
    {
        rightService.deleteRight(id);
        return 1;
    }

    @PostMapping("/uploadImage")
    public Map<String, String> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            if (file.isEmpty()) {
                response.put("success", "false");
                response.put("message", "请选择要上传的图片");
                return response;
            }

            // 验证文件大小
            if (file.getSize() > 2 * 1024 * 1024) {
                response.put("success", "false");
                response.put("message", "图片大小不能超过2MB");
                return response;
            }

            // 验证文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                response.put("success", "false");
                response.put("message", "只能上传图片文件");
                return response;
            }

            // 获取上传目录
            Path baseDir = Paths.get(uploadPath);
            Path imageDir = baseDir.resolve("images");

            // 创建目录
            try {
                Files.createDirectories(imageDir);
            } catch (IOException e) {
                logger.error("创建图片上传目录失败: {}", imageDir, e);
                response.put("success", "false");
                response.put("message", "创建上传目录失败");
                return response;
            }

            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ? 
                originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase() : ".jpg";
            String filename = UUID.randomUUID().toString() + extension;

            // 保存文件
            Path filePath = imageDir.resolve(filename);
            try {
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                logger.error("保存图片文件失败: {}", filePath, e);
                response.put("success", "false");
                response.put("message", "保存文件失败");
                return response;
            }

            // 返回文件访问路径
            String fileUrl = "/uploads/images/" + filename;
            response.put("success", "true");
            response.put("path", fileUrl);
            response.put("message", "图片上传成功");
            
            return response;
        } catch (Exception e) {
            logger.error("图片上传失败", e);
            response.put("success", "false");
            response.put("message", "图片上传失败: " + e.getMessage());
            return response;
        }
    }

    @PostMapping("/uploadAudio")
    public Map<String, String> uploadAudio(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            if (file.isEmpty()) {
                response.put("success", "false");
                response.put("message", "请选择要上传的音频文件");
                return response;
            }

            // 验证文件大小
            if (file.getSize() > 10 * 1024 * 1024) {
                response.put("success", "false");
                response.put("message", "音频文件大小不能超过10MB");
                return response;
            }

            // 验证文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("audio/")) {
                response.put("success", "false");
                response.put("message", "只能上传音频文件");
                return response;
            }

            // 获取上传目录
            Path baseDir = Paths.get(uploadPath);
            Path audioDir = baseDir.resolve("audio");

            // 创建目录
            try {
                Files.createDirectories(audioDir);
            } catch (IOException e) {
              //  logger.error("创建音频上传目录失败: {}", audioDir, e);
                response.put("success", "false");
                response.put("message", "创建上传目录失败");
                return response;
            }

            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ? 
                originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase() : ".mp3";
            String filename = UUID.randomUUID().toString() + extension;

            // 保存文件
            Path filePath = audioDir.resolve(filename);
            try {
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
               // logger.error("保存音频文件失败: {}", filePath, e);
                response.put("success", "false");
                response.put("message", "保存文件失败");
                return response;
            }

            // 返回文件访问路径
            String fileUrl = "/uploads/audio/" + filename;
            response.put("success", "true");
            response.put("path", fileUrl);
            response.put("message", "音频上传成功");
            
            return response;
        } catch (Exception e) {
          //  logger.error("音频上传失败", e);
            response.put("success", "false");
            response.put("message", "音频上传失败: " + e.getMessage());
            return response;
        }
    }

    @PostMapping("/insertProfile")
    public int insertProfile(@RequestBody Profile profile) {
        try {
            profileService.insertProfile(profile);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @PostMapping("/updateProfile")
    public int updateProfile(@RequestBody Profile profile) {
        try {
            profileService.updateProfile(profile);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @PostMapping("/insertHintCard")
    public int insertHintCard(@RequestBody HintCard hintCard){
        hintCardService.insertHintCard(hintCard);
        return 1;
    }

    @GetMapping("/findHintCard")
    public List<HintCard> findHintCards(HttpServletRequest req){
        HttpSession session = req.getSession();
        User u = (User) session.getAttribute("user");
        return hintCardService.selectById(u.getId());

    }
    @GetMapping("/selectScode")
    public Secrete getScode(HttpServletRequest req){
        HttpSession session = req.getSession();
        User u = (User) session.getAttribute("user");
        return profileService.selectScode(u.getId());
    }
}
