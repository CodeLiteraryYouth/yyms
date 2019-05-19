package com.leanin.mapper;

import com.leanin.domain.analysis.DeptAnalysis;
import com.leanin.domain.vo.MessageRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface MsgRecordMapper {

    void addMsgRecord(@Param("messageRecord") MessageRecord messageRecord);

    List<MessageRecord> findMsgRecordList(@Param("planType") Integer planType,@Param("msgThem") String msgThem,
                                          @Param("sendType") Integer sendType,@Param("patientId") String patientId);

    List<DeptAnalysis> deptFollowAnalysis(@Param("patientSource") Integer patientSource,
                                    @Param("planNum") String planNum,
                                    @Param("dept") String dept,
                                    @Param("startDate") String startDate,
                                    @Param("endDate") String endDate,
                                    @Param("planType")Integer planType);

}
