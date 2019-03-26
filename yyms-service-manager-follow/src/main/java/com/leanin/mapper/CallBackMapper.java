package com.leanin.mapper;

import java.util.List;

import com.leanin.domain.dto.CallBackDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.leanin.domain.CallBack;
import com.leanin.domain.CallBackDeal;

@Mapper
public interface CallBackMapper {

	/**
	 * 查询投诉表扬列表
	 * @param beginDate
	 * @param endDate
	 * @param dealStatus
	 * @param accuseWard
	 * @param backNum
	 * @return
	 */
	List<CallBackDto> findBackList(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("dealStatus") Integer dealStatus,
                                   @Param("accuseWard") String accuseWard, @Param("backNum") String backNum, @Param("status") int status);
	
	/**
	 * 查询投诉表扬详情
	 * @param backNum
	 * @return
	 */
	CallBackDto findBackById(@Param("backNum") String backNum, @Param("status") int status);
	
	/**
	 * 发起投诉/表扬
	 * @param callBack
	 * @return
	 */
	int addCallBack(CallBack callBack);
	
	/**
	 * 添加投诉表扬的处理信息
	 * @param callBackDeal
	 * @return
	 */
	int addCallBackDeal(CallBackDeal callBackDeal);
	
	/**
	 * 修改投诉表扬的处理信息
	 * @param callBackDeal
	 * @return
	 */
	int updateCallBackDeal(CallBackDeal callBackDeal);

	CallBackDeal findBackDetail(@Param("callBackNum") String callBackNum);
}
