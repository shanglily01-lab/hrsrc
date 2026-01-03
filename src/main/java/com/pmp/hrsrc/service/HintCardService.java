package com.pmp.hrsrc.service;

import com.pmp.hrsrc.entity.HintCard;

import java.util.List;

public interface HintCardService {

    List<HintCard> selectAll();
    void insertHintCard(HintCard hintCard);
    List<HintCard> selectById(int uid);
    int findMaxId();
}
