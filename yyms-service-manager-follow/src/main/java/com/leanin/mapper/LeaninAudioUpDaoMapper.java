package com.leanin.mapper;


import com.leanin.dao.BaseMapper;
import com.leanin.domain.LeaninAudioUpDao;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;

public interface LeaninAudioUpDaoMapper extends BaseMapper<LeaninAudioUpDao> {
	/**
	 * 根据calluuid查询记录
	 * @param callUuid
	 * @return
	 */
	LeaninAudioUpDao selectByCallUuid(@Param("callUuid")String callUuid);
}