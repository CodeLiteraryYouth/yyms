package com.leanin.service;


import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.MessageRecord;
import com.leanin.domain.vo.MsgInfoVo;
import com.leanin.domain.vo.OnlineEdu;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MsgInfoService {

	/**
	 * 根据分类ID查询提醒库信息
	 * @param typeId
	 * @param pageSize
	 * @return
	 */
	DataOutResponse findMsgListByTypeId(Integer page, Integer pageSize, Long typeId, String msgName);
	/**
	 * 更改提醒库状态
	 * @param msgId
	 * @return
	 */
	DataOutResponse updateMsgStatus(String msgId, int status);

    /**
     * 增加提醒库内容
     * @param record
     * @return
     */
	DataOutResponse addMsgInfo(MsgInfoVo record);

    /**
     * 根据表单号查询提醒库信息
     * @param msgId
     * @return
     */
	DataOutResponse findMsgInfoById(String msgId);

    /**
      * 编辑提醒库内容
     * @param record
     * @return
     */
	DataOutResponse updateMsgInfo(MsgInfoVo record);

	//手动发送短信
    DataOutResponse sendMessage(String[] longs,Integer type,HttpServletRequest request/*,String formId,String msgId*/);

    DataOutResponse sendEduMessage(List<OnlineEdu> onlineEdus);

	DataOutResponse sendCommonMsg(List<MessageRecord> messageRecord, HttpServletRequest request);

}
