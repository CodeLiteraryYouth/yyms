package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.MessageTopicVo;

public interface MessageTopicService {

	/**
	 * 查询短信主题列表
	 * @param msgTopicName
	 * @return
	 */
	DataOutResponse findMsgTopicList(int page, int pageSize, String msgTopicName);
	/**
	 * 修改短信主题状态
	 * @param msgTopicId
	 * @param status
	 * @return
	 */
	DataOutResponse updateTopicStatus(String msgTopicId, int status);

    /**
     * 增加短信主题信息
     * @param record
     * @return
     */
	DataOutResponse addMsgTopic(MessageTopicVo record);

    /**
     * 根据ID查询短信主题信息
     * @param msgTopicId
     * @return
     */
	DataOutResponse findMsgTopicById(String msgTopicId);

    /**
     * 修改短信主题内容
     * @param record
     * @return
     */
	DataOutResponse updateMsgTopic(MessageTopicVo record);
}
