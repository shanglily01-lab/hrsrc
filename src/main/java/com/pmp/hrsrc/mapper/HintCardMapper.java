package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.HintCard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HintCardMapper {

    List<HintCard> selectAll();
    void insertHintCard(HintCard hintCard);
    List<HintCard> selectById(int uid);
    int findMaxId();
}
