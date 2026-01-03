package com.pmp.hrsrc.mapper;

import com.pmp.hrsrc.entity.Zpacc;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ZpaccMapper {

    List<Zpacc> selectAll();
    int insertZpacc(Zpacc zpacc);
    int findMaxId();
    Zpacc selectById(int id);
    int updateZpacc(Zpacc zpacc);
    int  deleteZpacc(int id);
}
