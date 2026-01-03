package com.pmp.hrsrc.service.impl;


import com.pmp.hrsrc.entity.HintCard;
import com.pmp.hrsrc.mapper.HintCardMapper;
import com.pmp.hrsrc.service.HintCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HintCardServiceImpl implements HintCardService {
    @Autowired
    HintCardMapper hintCardMapper;

    @Override
    public List<HintCard> selectAll() {
        return hintCardMapper.selectAll();
    }

    @Override
    public void insertHintCard(HintCard hintCard) {
        hintCardMapper.insertHintCard(hintCard);
    }

    @Override
    public List<HintCard> selectById(int uid) {
        return hintCardMapper.selectById(uid);
    }

    @Override
    public int findMaxId() {
        return hintCardMapper.findMaxId();
    }
}
