package com.leanin.schdule.mapper;

import com.leanin.domain.vo.MessageRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface MsgRecordMapper {

    void addMsgRecord(@Param("messageRecord") MessageRecord messageRecord);
}
