package com.leanin.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author CPJ.
 * @date 2019/5/31.
 * @time 14:39.
 */
@Data
public class PhoneRecordVo {

    private Date phoneTime;     //拨打电话时间

    private List<WardInfoVo> wardInfoVos; //科室

    private String medicalWorkerName;   //医务工作人员姓名

    private String phoneNum;        //患者号码

    private String callDuration;    //通话时长

    private Long callInfoId;        //通话记录id
}
