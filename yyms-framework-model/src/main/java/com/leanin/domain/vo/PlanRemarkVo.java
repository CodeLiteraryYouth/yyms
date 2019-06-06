package com.leanin.domain.vo;

import com.leanin.domain.dao.RemarkDao;
import lombok.Data;

import java.util.List;

/**
 * @author CPJ.
 * @date 2019/6/6.
 * @time 10:55.
 */
@Data
public class PlanRemarkVo extends RemarkDao {

    private List<WardInfoVo> wardInfoVos;   //评论人科室

    private String remarkerName;    //评论人 名称
}
