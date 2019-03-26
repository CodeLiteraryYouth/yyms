package com.leanin.service;
import com.leanin.domain.CallBack;
import com.leanin.domain.CallBackDeal;
import com.leanin.domain.response.DataOutResponse;

public interface CallBackService {

	/**
	 * 查询投诉表扬列表
	 * @param beginDate
	 * @param endDate
	 * @param dealStatus
	 * @param accuseWard
	 * @param backNum
	 * @return
	 */
	DataOutResponse findBackList(int page, int pageSize, String beginDate, String endDate, Integer dealStatus, String accuseWard, String backNum, int status);
	
	/**
	 * 查询投诉表扬详情
	 * @param backNum
	 * @return
	 */
	DataOutResponse findBackById(String backNum, int status);
	
	/**
	 * 发起投诉/表扬
	 * @param callBack
	 * @return
	 */
	DataOutResponse addCallBack(CallBack callBack);
	
	/**
	 * 修改投诉表扬的处理信息
	 * @param callBackDeal
	 * @return
	 */
	DataOutResponse updateCallBackDeal(CallBackDeal callBackDeal);

	DataOutResponse findBackDealByBackNum(String backNum);
}
