package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.entity.Require;
import com.pmp.hrsrc.service.RequireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reqs")
public class ReqController {

    @Autowired
    private RequireService reqService;

    @GetMapping
    public ResponseEntity<List<Require>> getAllReqs(@RequestParam(required = false) Integer pid) {
        try {
            List<Require> reqs = reqService.selectAll(pid);
            return ResponseEntity.ok(reqs);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Require> getReqById(@PathVariable Integer rid) {
        try {
            Require req = reqService.findById(rid);
            if (req != null) {
                return ResponseEntity.ok(req);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createReq(@RequestBody Require req) {
        try {
            reqService.insertRequire(req);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "需求创建成功");
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "需求创建失败: " + e.getMessage());
            response.put("success", false);
            return ResponseEntity.status(500).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateReq(@PathVariable Integer id, @RequestBody Require req) {
        try {
            req.setId(id);
            reqService.updateRequire(req);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "需求更新成功");
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "需求更新失败: " + e.getMessage());
            response.put("success", false);
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteReq(@PathVariable Integer id) {
        try {
            reqService.deleteRequire(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "需求删除成功");
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "需求删除失败: " + e.getMessage());
            response.put("success", false);
            return ResponseEntity.status(500).body(response);
        }
    }
} 