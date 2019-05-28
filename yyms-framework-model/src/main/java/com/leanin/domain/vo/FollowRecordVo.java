package com.leanin.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 患者档案个人随访记录展示
 * @author CPJ.
 * @date 2019/5/28.
 * @time 13:59.
 */
@Data
public class FollowRecordVo {

    private Date sendDate;                  //发送时间

    private List<WardInfoVo> wardInfoVos;   //科室集合

    private String senderName;              //发送人名称

    private String handlerSuggess;          //处理意见

    private String formName;                //表单名称

    private String formId;                  //表单号

    private String formStat;                //表单状态

    private Long formRecordId;              //表单记录id
}
