package com.leanin.mapper;



import com.leanin.dao.BaseMapper;
import com.leanin.domain.LeaninCallLogInfoDao;
import com.leanin.domain.analysis.DeptAnalysis;
import com.leanin.dto.CallLogInfoDto;
import com.leanin.dto.query.QueryCallLogInfoDto;
import com.leanin.vo.CallLoginfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import javax.annotation.Resource;
import java.util.List;
public interface LeaninCallLogInfoDaoMapper extends BaseMapper<LeaninCallLogInfoDao> {

    /**
     * 分页查询
     * @param queryCallLogInfoDto
     * @return
     */
    List<CallLoginfoVo> findList(@Param("data") QueryCallLogInfoDto queryCallLogInfoDto);

    /**
     * 查看
     * @param callLogInfoId
     * @return
     */
    CallLogInfoDto findCallLogInfoById( @Param("callLogInfoId") Long callLogInfoId);

    List<LeaninCallLogInfoDao> exportCallLogExcel(QueryCallLogInfoDto queryCallLogInfoDto);

    List<DeptAnalysis> deptFollowAnalysis(@Param("patientSource") Integer patientSource,@Param("planNum") String planNum,@Param("dept") String dept,
                                          @Param("startDate") String startDate,@Param("endDate") String endDate,@Param("planType") Integer planType);

}