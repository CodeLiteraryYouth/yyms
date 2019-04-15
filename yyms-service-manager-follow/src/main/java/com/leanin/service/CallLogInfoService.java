package com.leanin.service;

import com.leanin.domain.LeaninCallLogInfoDao;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.dto.CallLogInfoDto;
import com.leanin.dto.query.QueryCallLogInfoDto;

/**
 * @ClassName CallLogInfoService
 * @Description 通话记录接口
 * @Author zliu
 * @Date 2019/4/10 10:40
 * @ModifyDate 2019/4/10 10:40
 * @Version 1.0
 */
public interface CallLogInfoService{
    /**
     * 查询历史纪录
     * @param queryCallLogInfoDto
     * @return
     */
    DataOutResponse findList(QueryCallLogInfoDto queryCallLogInfoDto);

    /**
     * 查看
     * @param callLogInfoId
     * @return
     */
    DataOutResponse detail(Long callLogInfoId);

    /**
     * 修改 remark
     * @param leaninCallLogInfoDao
     * @return
     */
    DataOutResponse update(LeaninCallLogInfoDao leaninCallLogInfoDao);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    DataOutResponse delete(String ids);
    /**
     * 插入通话记录
     * @param callLogInfoDto
     * @return
     */
	DataOutResponse insertCallLog(CallLogInfoDto callLogInfoDto);
}