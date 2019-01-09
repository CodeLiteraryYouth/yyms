package com.leanin.mapper;


import com.leanin.domain.vo.HospitalAreaVo;

public interface HospitalAreaMapper {
    int deleteByPrimaryKey(Long areaId);

    int insert(HospitalAreaVo record);

    int insertSelective(HospitalAreaVo record);

    HospitalAreaVo selectByPrimaryKey(Long areaId);

    int updateByPrimaryKeySelective(HospitalAreaVo record);

    int updateByPrimaryKey(HospitalAreaVo record);
}