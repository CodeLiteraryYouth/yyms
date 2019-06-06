package com.leanin.mapper;

import com.leanin.domain.dao.RemarkDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author CPJ.
 * @date 2019/6/6.
 * @time 9:27.
 */
@Mapper
public interface PlanRemarkMapper {

    List<RemarkDao> findByParam(@Param("planNum") String planNum,@Param("formNum") String formNum,@Param("planType") Integer planType,@Param("questionId") String questionId);
}
