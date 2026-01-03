package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.entity.Zpacc;
import com.pmp.hrsrc.service.ZpaccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/zpaccs")
public class ZpaccController {
    @Autowired
    private ZpaccService zpaccService;

    // 获取所有招聘账号
    @GetMapping
    public List<Zpacc> getAllZpaccs() {
        return zpaccService.selectAll();
    }

    // 根据ID获取招聘账号
    @GetMapping("/{id}")
    public Zpacc getZpaccById(@PathVariable Integer id) {
        return zpaccService.selectById(id);
    }

    // 创建新的招聘账号
    @PostMapping
    public ResponseEntity<?> createZpacc(@RequestBody Zpacc zpacc) {
        try {
            zpacc.setId(zpaccService.findMaxId());
            int result = zpaccService.insertZpacc(zpacc);
            if (result > 0) {
                return ResponseEntity.ok(zpacc);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "保存失败");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "服务器错误: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 更新招聘账号
    @PutMapping("/{id}")
    public ResponseEntity<?> updateZpacc(@PathVariable Integer id, @RequestBody Zpacc zpacc) {
        try {
            zpacc.setId(id);
            int result = zpaccService.updateZpacc(zpacc);
            if (result > 0) {
                return ResponseEntity.ok(zpacc);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "更新失败");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "服务器错误: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 删除招聘账号
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteZpacc(@PathVariable Integer id) {
        try {
            int result = zpaccService.deleteZpacc(id);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "删除成功");
                return ResponseEntity.ok().body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "删除失败");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "服务器错误: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 获取招聘账号列表页面
    @GetMapping("/list")
    public String getZpaccList(Model model) {
        List<Zpacc> zps = zpaccService.selectAll();
        model.addAttribute("zps", zps);
        return "zpaccs";
    }
} 