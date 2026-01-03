package com.pmp.hrsrc.controller;

import com.pmp.hrsrc.entity.Announcement;
import com.pmp.hrsrc.service.AnnouncementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@Api(tags = "公告管理接口")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/published")
    @ApiOperation("获取所有已发布的公告")
    public ResponseEntity<List<Announcement>> getAllPublished() {
        return ResponseEntity.ok(announcementService.findAllPublished());
    }

    @GetMapping
    @ApiOperation("获取所有公告")
    public ResponseEntity<List<Announcement>> getAll() {
        return ResponseEntity.ok(announcementService.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID获取公告")
    public ResponseEntity<Announcement> getById(@PathVariable Long id) {
        Announcement announcement = announcementService.findById(id);
        return announcement != null ? ResponseEntity.ok(announcement) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    @ApiOperation("创建新公告")
    public ResponseEntity<Announcement> create(@RequestBody Announcement announcement) {
        return ResponseEntity.ok(announcementService.create(announcement));
    }

    @PutMapping("/{id}")
    @ApiOperation("更新公告")
    public ResponseEntity<Announcement> update(@PathVariable Long id, @RequestBody Announcement announcement) {
        announcement.setId(id);
        return ResponseEntity.ok(announcementService.update(announcement));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除公告")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        announcementService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/publish")
    @ApiOperation("发布公告")
    public ResponseEntity<Announcement> publish(@PathVariable Long id) {
        return ResponseEntity.ok(announcementService.publish(id));
    }

    @PutMapping("/{id}/archive")
    @ApiOperation("归档公告")
    public ResponseEntity<Announcement> archive(@PathVariable Long id) {
        return ResponseEntity.ok(announcementService.archive(id));
    }
} 