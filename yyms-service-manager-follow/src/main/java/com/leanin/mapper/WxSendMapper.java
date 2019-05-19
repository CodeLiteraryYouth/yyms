package com.leanin.mapper;

import com.leanin.domain.analysis.DeptAnalysis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author CPJ.
 * @date 2019/5/19.
 * @time 19:33.
 */
@Mapper
public interface WxSendMapper {

    List<DeptAnalysis> deptFollowAnalysis(@Param("patientSource") Integer patientSource,
                                          @Param("planNum") String planNum,
                                          @Param("dept") String dept,
                                          @Param("startDate") String startDate,
                                          @Param("endDate") String endDate,
                                          @Param("planType")Integer planType);

}
