package com.leanin.mapper;

import com.leanin.domain.vo.MsgInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
public interface MsgInfoMapper {
	
	/**
	 * 根据分类ID查询提醒库信息
	 * @param typeId
	 * @param msgName
	 * @return
	 */
	List<MsgInfoVo> findMsgListByTypeId(@Param("typeId") Long typeId, @Param("msgName") String msgName);
	/**
	 * 更改提醒库状态
	 * @param msgId
	 * @return
	 */
    int updateMsgStatus(@Param("msgId") String msgId, @Param("status") Integer status);

    /**
     * 增加提醒库内容
     * @param record
     * @return
     */
    int addMsgInfo(MsgInfoVo record);

    /**
     * 根据表单号查询提醒库信息
     * @param msgId
     * @return
     */
	MsgInfoVo findMsgInfoById(String msgId);
    
    /**
     * 根据姓名查询提醒库信息
     * @param msgName
     * @return
     */
	MsgInfoVo findMsgInfoByName(@Param("msgName") String msgName);

    /**
      * 编辑提醒库内容
     * @param record
     * @return
     */
    int updateMsgInfo(MsgInfoVo record);

}