package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.entity.Demark;
import com.pmp.hrsrc.service.DemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 事项管理控制器
 * 提供事项的增删改查等REST接口
 */
@RestController
@RequestMapping("/api/demarks")
public class DemarkController {

    @Autowired
    private DemarkService demarkService;

    /**
     * 获取所有事项列表
     * @return 事项列表
     */
    @GetMapping
    public ResponseEntity<List<Demark>> getAllDemarks() {
        List<Demark> demarks = demarkService.selectAll();
        return ResponseEntity.ok(demarks);
    }

    /**
     * 根据ID获取单个事项
     * @param id 事项ID
     * @return 事项详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<Demark> getDemarkById(@PathVariable int id) {
        Demark demark = demarkService.selectById(id);
        if (demark != null) {
            return ResponseEntity.ok(demark);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 创建新事项
     * @param demark 事项信息
     * @return 新创建的事项ID
     */
    @PostMapping
    public ResponseEntity<Integer> createDemark(@RequestBody Demark demark) {
        if (demark == null) {
            return ResponseEntity.badRequest().build();
        }
        int id = demarkService.findMaxId();
        demark.setId(id);
        demarkService.insertDemark(demark);
        return ResponseEntity.ok(id + 1);
    }

    /**
     * 更新现有事项
     * @param id 事项ID
     * @param demark 更新的事项信息
     * @return 更新后的最大ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateDemark(@PathVariable int id, @RequestBody Demark demark) {
        if (demark == null || demark.getId() != id) {
            return ResponseEntity.badRequest().build();
        }
        demarkService.updateDemark(demark);
        return ResponseEntity.ok(demarkService.findMaxId());
    }

    /**
     * 删除事项
     * @param id 要删除的事项ID
     * @return 删除后的最大ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteDemark(@PathVariable int id) {
        demarkService.deleteDemark(id);
        return ResponseEntity.ok(demarkService.findMaxId());
    }
} 