package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.Announcement;
import java.util.List;

public interface AnnouncementService {
    List<Announcement> findAllPublished();
    List<Announcement> findAll();
    Announcement findById(Long id);
    Announcement create(Announcement announcement);
    Announcement update(Announcement announcement);
    void delete(Long id);
    Announcement publish(Long id);
    Announcement archive(Long id);
} 