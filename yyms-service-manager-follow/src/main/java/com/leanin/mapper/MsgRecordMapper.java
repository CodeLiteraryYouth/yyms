package com.leanin.mapper;

import com.leanin.domain.vo.MessageRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface MsgRecordMapper {

    void addMsgRecord(@Param("messageRecord") MessageRecord messageRecord);

    List<MessageRecord> findMsgRecordList(@Param("planType") Integer planType,@Param("msgThem") String msgThem,@Param("sendType") Integer sendType);
}
