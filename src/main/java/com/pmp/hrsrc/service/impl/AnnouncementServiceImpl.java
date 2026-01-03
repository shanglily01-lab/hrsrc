package com.pmp.hrsrc.service.impl;

import com.pmp.hrsrc.entity.Announcement;
import com.pmp.hrsrc.mapper.AnnouncementMapper;
import com.pmp.hrsrc.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public List<Announcement> findAllPublished() {
        return announcementMapper.findAllPublished();
    }

    @Override
    public List<Announcement> findAll() {
        return announcementMapper.findAll();
    }

    @Override
    public Announcement findById(Long id) {
        return announcementMapper.findById(id);
    }

    @Override
    @Transactional
    public Announcement create(Announcement announcement) {
        if (announcement.getStatus() == null) {
            announcement.setStatus("DRAFT");
        }
        if (announcement.getPublishDate() == null) {
            announcement.setPublishDate(new Date());
        }
        announcementMapper.insert(announcement);
        return announcement;
    }

    @Override
    @Transactional
    public Announcement update(Announcement announcement) {
        Announcement existing = announcementMapper.findById(announcement.getId());
        if (existing == null) {
            throw new RuntimeException("Announcement not found");
        }
        announcementMapper.update(announcement);
        return announcement;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        announcementMapper.delete(id);
    }

    @Override
    @Transactional
    public Announcement publish(Long id) {
        Announcement announcement = announcementMapper.findById(id);
        if (announcement == null) {
            throw new RuntimeException("Announcement not found");
        }
        announcement.setStatus("PUBLISHED");
        announcement.setPublishDate(new Date());
        announcementMapper.update(announcement);
        return announcement;
    }

    @Override
    @Transactional
    public Announcement archive(Long id) {
        Announcement announcement = announcementMapper.findById(id);
        if (announcement == null) {
            throw new RuntimeException("Announcement not found");
        }
        announcement.setStatus("ARCHIVED");
        announcementMapper.update(announcement);
        return announcement;
    }
} 