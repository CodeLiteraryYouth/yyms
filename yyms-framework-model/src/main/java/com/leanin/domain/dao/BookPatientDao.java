package com.leanin.domain.dao;

import lombok.Data;
import lombok.ToString;

/**
 * 病人预约信息
 */
@Data
@ToString
public class BookPatientDao  {

    /**
     * 病人唯一标识
     */
    private String patientId;
    /**
     * 病人姓名
     */
    private String patientName;

    /**
     * 1:男 2:女
     */
    private int sex;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 病人类型
     */
    private int patientType;

    /**
     * 管理员ID
     */
    private Long adminId;

    /**
     *预约科室ID
     */
    private String deptId;
    /**
     * 预约科室名称
     */
    private String deptName;

    /**
     * 预约日期
     */
    private String bookDate;

    /**
     * 预约就诊日期
     */
    private String seeDocDate;

    /**
     * 1：上午 2：下午
     */
    private int bookType;

    /**
     * 预约来源(1:PC 2:公众号)
     */
    private int bookResource;

    /**
     * 预约医生ID
     */
    private String doctorId;

    /**
     * 预约医生姓名
     */
    private String doctorName;

    /**
     * 就诊状态(0:未就诊 1：已就诊)
     */
    private int adviceType;

    /**
     * 挂号费
     */
    private double bookMoney;

    /**
     * 医疗卡号
     */
    private String medicalCard;
}
