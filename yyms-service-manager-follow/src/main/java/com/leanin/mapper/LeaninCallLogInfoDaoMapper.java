package com.leanin.mapper;



import com.leanin.dao.BaseMapper;
import com.leanin.domain.LeaninCallLogInfoDao;
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
    List<CallLoginfoVo> findList(QueryCallLogInfoDto queryCallLogInfoDto);

    /**
     * 查看
     * @param callLogInfoId
     * @return
     */
    CallLogInfoDto findCallLogInfoById( @Param("callLogInfoId") Long callLogInfoId);
}