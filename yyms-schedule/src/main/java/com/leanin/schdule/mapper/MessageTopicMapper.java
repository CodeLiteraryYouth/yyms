package com.leanin.schdule.mapper;


import com.leanin.domain.vo.MessageTopicVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 短信主题的Mapper
 * @author Administrator
 */
@Mapper
public interface MessageTopicMapper {

    /**
     * 查询短信主题列表
     * @param msgTopicTitle
     * @return
     */
    List<MessageTopicVo> findMsgTopicList(@Param("msgTopicTitle") String msgTopicTitle);
    /**
     * 修改短信主题状态
     * @param msgTopicId
     * @param status
     * @return
     */
//    int updateTopicStatus(@Param("msgTopicId") String msgTopicId,@Param("status") int status);

    /**
     * 增加短信主题信息
     * @param record
     * @return
     */
//    int addMsgTopic(MessageTopicVo record);

    /**
     * 根据ID查询短信主题信息
     * @param msgTopicId
     * @return
     */
//    MessageTopicVo findMsgTopicById(String msgTopicId);

    /**
     * 根据短信主题名查询短信主题信息
     * @param msgTopicName
     * @return
     */
//    MessageTopicVo findMsgTopicByName(String msgTopicName);

    /**
     * 修改短信主题内容
     * @param record
     * @return
     */
//    int updateMsgTopic(MessageTopicVo record);

//    List<MessageTopicVo> findList();
}