package com.leanin.mapper;

import com.leanin.domain.vo.SatisfyPlanVo;

public interface SatisfyPlanMapper {
    int deleteByPrimaryKey(String planSatisfyNum);

    int insert(SatisfyPlanVo record);

    int insertSelective(SatisfyPlanVo record);

    SatisfyPlanVo selectByPrimaryKey(String planSatisfyNum);

    int updateByPrimaryKeySelective(SatisfyPlanVo record);

    int updateByPrimaryKey(SatisfyPlanVo record);
}