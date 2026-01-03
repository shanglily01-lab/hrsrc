package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.Announcement;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnnouncementMapper {
    
    @Select("SELECT * FROM announcements WHERE status = 'PUBLISHED' ORDER BY id DESC")
    List<Announcement> findAllPublished();

    @Select("SELECT * FROM announcements ORDER BY id DESC")
    List<Announcement> findAll();

    @Select("SELECT * FROM announcements WHERE id = #{id} ORDER BY id DESC")
    Announcement findById(Long id);

    @Insert("INSERT INTO announcements (title, content, publishDate, publisherId, publisherName, status) " +
            "VALUES (#{title}, #{content}, #{publishDate}, #{publisherId}, #{publisherName}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Announcement announcement);

    @Update("UPDATE announcements SET title = #{title}, content = #{content}, " +
            "publishDate = #{publishDate}, publisherId = #{publisherId}, " +
            "publisherName = #{publisherName}, status = #{status} WHERE id = #{id}")
    int update(Announcement announcement);

    @Delete("DELETE FROM announcements WHERE id = #{id}")
    int delete(Long id);
} 