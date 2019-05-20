package com.leanin.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "leanin_call_log_info")
public class LeaninCallLogInfoDao extends BaseRowModel {

    @Id
    @Column(name = "call_log_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long callLogInfoId;

    /**
     * 随访计划id
     */
    @Column(name = "follow_plan_id")
    private String followPlanId;

    /**
     * 科室id
     */
    @Column(name = "section_id")
    private String sectionId;

    /**
     * 录音id
     */
    @Column(name = "autio_up_id")
    private Long autioUpId;
    /**
     * 来电唯一标识
     */
    @Column(name = "call_uuid")
    private String callUuid;
    /**
     * 随访计划名称
     */
    @Column(name = "follow_plan_name")
    @ExcelProperty(value = "计划",index = 0)
    private String followPlanName;

    /**
     * 科室名称
     */
    @Column(name = "section_name")
    @ExcelProperty(value = "科室",index = 1)
    private String sectionName;

    /**
     * 呼叫类型（0-1-2）
     */
    @Column(name = "call_type")
    private Integer callType;

    /**
     * 呼叫人员id
     */
    @Column(name = "call_creater_id")
    private Long callCreaterId;

    /**
     * 呼叫人员姓名
     */
    @Column(name = "call_creater_name")
    @ExcelProperty(value = "医务工作人员",index = 2)
    private String callCreaterName;

    /**
     * 客户id
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * 客户姓名
     */
    @Column(name = "customer_name")
    @ExcelProperty(value = "患者",index = 3)
    private String customerName;

    /**
     * 呼叫人员号码
     */
    @Column(name = "call_creater_number")
    private String callCreaterNumber;

    /**
     * 客户号码
     */
    @ExcelProperty(value = "通话号码",index = 4)
    @Column(name = "customer_number")
    private String customerNumber;
    /**
     * 开始拨打时间
     */
    @Column(name = "call_start_time")
    private Date callStartTime;

    /**
     * 结束拨打时间
     */
    @Column(name = "call_end_time")
    private Date callEndTime;

    /**
     * 通话时长
     */
    @ExcelProperty(value = "通话时长",index = 6)
    @Column(name = "holding_time")
    private String holdingTime;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注",index = 7)
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人id
     */
    @Column(name = "createor_id")
    private Long createorId;

    /**
     * 是否删除(Y-是，N-否)
     */
    @Column(name = "is_delete")
    private String isDelete;

    @ExcelProperty(value = "拨打时间",index = 5)
    private String startTime;

    @Column(name = "plan_patient_id")
    private Long planPatientId;     //计划患者主键

    @Column(name = "plan_type")
    private Integer planType;       //计划类型  1 随访 2 宣教 3 满意度 4 其他

    @Column(name = "patient_ward")
    private String patientWard;     //患者科室  科室中文名

    @Column(name = "next_date")
    private Date nextDate;          //  计划患者 随访/宣教/满意度 发送时间

    @Column(name = "patient_source")
    private Integer patientSource;  // 患者来源
    /**
     * @return call_log_info_id
     */
    public Long getCallLogInfoId() {
        return callLogInfoId;
    }

    /**
     * @param callLogInfoId
     */
    public void setCallLogInfoId(Long callLogInfoId) {
        this.callLogInfoId = callLogInfoId;
    }

    /**
     * 获取随访计划id
     *
     * @return follow_plan_id - 随访计划id
     */
    public String getFollowPlanId() {
        return followPlanId;
    }

    /**
     * 设置随访计划id
     *
     * @param followPlanId 随访计划id
     */
    public void setFollowPlanId(String followPlanId) {
        this.followPlanId = followPlanId;
    }

    /**
     * 获取科室id
     *
     * @return section_id - 科室id
     */
    public String getSectionId() {
        return sectionId;
    }

    /**
     * 设置科室id
     *
     * @param sectionId 科室id
     */
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * 获取录音id
     *
     * @return autio_up_id - 录音id
     */
    public Long getAutioUpId() {
        return autioUpId;
    }

    /**
     * 设置录音id
     *
     * @param autioUpId 录音id
     */
    public void setAutioUpId(Long autioUpId) {
        this.autioUpId = autioUpId;
    }

    /**
     * 获取随访计划名称
     *
     * @return follow_plan_name - 随访计划名称
     */
    public String getFollowPlanName() {
        return followPlanName;
    }

    /**
     * 设置随访计划名称
     *
     * @param followPlanName 随访计划名称
     */
    public void setFollowPlanName(String followPlanName) {
        this.followPlanName = followPlanName;
    }

    /**
     * 获取科室名称
     *
     * @return section_name - 科室名称
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * 设置科室名称
     *
     * @param sectionName 科室名称
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * 获取呼叫类型（0-1-2）
     *
     * @return call_type - 呼叫类型（0-1-2）
     */
    public Integer getCallType() {
        return callType;
    }

    /**
     * 设置呼叫类型（0-1-2）
     *
     * @param callType 呼叫类型（0-1-2）
     */
    public void setCallType(Integer callType) {
        this.callType = callType;
    }

    /**
     * 获取呼叫人员id
     *
     * @return call_creater_id - 呼叫人员id
     */
    public Long getCallCreaterId() {
        return callCreaterId;
    }

    /**
     * 设置呼叫人员id
     *
     * @param callCreaterId 呼叫人员id
     */
    public void setCallCreaterId(Long callCreaterId) {
        this.callCreaterId = callCreaterId;
    }

    /**
     * 获取呼叫人员姓名
     *
     * @return call_creater_name - 呼叫人员姓名
     */
    public String getCallCreaterName() {
        return callCreaterName;
    }

    /**
     * 设置呼叫人员姓名
     *
     * @param callCreaterName 呼叫人员姓名
     */
    public void setCallCreaterName(String callCreaterName) {
        this.callCreaterName = callCreaterName;
    }

    public String getCallUuid() {
		return callUuid;
	}

	public void setCallUuid(String callUuid) {
		this.callUuid = callUuid;
	}

	/**
     * 获取客户id
     *
     * @return customer_id - 客户id
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * 设置客户id
     *
     * @param customerId 客户id
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取客户姓名
     *
     * @return customer_name - 客户姓名
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 设置客户姓名
     *
     * @param customerName 客户姓名
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 获取呼叫人员号码
     *
     * @return call_creater_number - 呼叫人员号码
     */
    public String getCallCreaterNumber() {
        return callCreaterNumber;
    }

    /**
     * 设置呼叫人员号码
     *
     * @param callCreaterNumber 呼叫人员号码
     */
    public void setCallCreaterNumber(String callCreaterNumber) {
        this.callCreaterNumber = callCreaterNumber;
    }

    /**
     * 获取客户号码
     *
     * @return customer_number - 客户号码
     */
    public String getCustomerNumber() {
        return customerNumber;
    }

    /**
     * 设置客户号码
     *
     * @param customerNumber 客户号码
     */
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCallStartTime() {
		return callStartTime;
	}

	public void setCallStartTime(Date callStartTime) {
		this.callStartTime = callStartTime;
	}

	public Date getCallEndTime() {
		return callEndTime;
	}

	public void setCallEndTime(Date callEndTime) {
		this.callEndTime = callEndTime;
	}

	public String getHoldingTime() {
		return holdingTime;
	}

	public void setHoldingTime(String holdingTime) {
		this.holdingTime = holdingTime;
	}

	/**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人id
     *
     * @return createor_id - 创建人id
     */
    public Long getCreateorId() {
        return createorId;
    }

    /**
     * 设置创建人id
     *
     * @param createorId 创建人id
     */
    public void setCreateorId(Long createorId) {
        this.createorId = createorId;
    }

    /**
     * 获取是否删除(Y-是，N-否)
     *
     * @return is_delete - 是否删除(Y-是，N-否)
     */
    public String getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除(Y-是，N-否)
     *
     * @param isDelete 是否删除(Y-是，N-否)
     */
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public Long getPlanPatientId() {
        return planPatientId;
    }

    public void setPlanPatientId(Long planPatientId) {
        this.planPatientId = planPatientId;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public String getPatientWard() {
        return patientWard;
    }

    public void setPatientWard(String patientWard) {
        this.patientWard = patientWard;
    }

    public Date getNextDate() {
        return nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

    public Integer getPatientSource() {
        return patientSource;
    }

    public void setPatientSource(Integer patientSource) {
        this.patientSource = patientSource;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        LeaninCallLogInfoDao other = (LeaninCallLogInfoDao) that;
        return (this.getCallLogInfoId() == null ? other.getCallLogInfoId() == null : this.getCallLogInfoId().equals(other.getCallLogInfoId()))
            && (this.getFollowPlanId() == null ? other.getFollowPlanId() == null : this.getFollowPlanId().equals(other.getFollowPlanId()))
            && (this.getSectionId() == null ? other.getSectionId() == null : this.getSectionId().equals(other.getSectionId()))
            && (this.getAutioUpId() == null ? other.getAutioUpId() == null : this.getAutioUpId().equals(other.getAutioUpId()))
            && (this.getFollowPlanName() == null ? other.getFollowPlanName() == null : this.getFollowPlanName().equals(other.getFollowPlanName()))
            && (this.getSectionName() == null ? other.getSectionName() == null : this.getSectionName().equals(other.getSectionName()))
            && (this.getCallType() == null ? other.getCallType() == null : this.getCallType().equals(other.getCallType()))
            && (this.getCallCreaterId() == null ? other.getCallCreaterId() == null : this.getCallCreaterId().equals(other.getCallCreaterId()))
            && (this.getCallCreaterName() == null ? other.getCallCreaterName() == null : this.getCallCreaterName().equals(other.getCallCreaterName()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getCustomerName() == null ? other.getCustomerName() == null : this.getCustomerName().equals(other.getCustomerName()))
            && (this.getCallCreaterNumber() == null ? other.getCallCreaterNumber() == null : this.getCallCreaterNumber().equals(other.getCallCreaterNumber()))
            && (this.getCustomerNumber() == null ? other.getCustomerNumber() == null : this.getCustomerNumber().equals(other.getCustomerNumber()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateorId() == null ? other.getCreateorId() == null : this.getCreateorId().equals(other.getCreateorId()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCallLogInfoId() == null) ? 0 : getCallLogInfoId().hashCode());
        result = prime * result + ((getFollowPlanId() == null) ? 0 : getFollowPlanId().hashCode());
        result = prime * result + ((getSectionId() == null) ? 0 : getSectionId().hashCode());
        result = prime * result + ((getAutioUpId() == null) ? 0 : getAutioUpId().hashCode());
        result = prime * result + ((getFollowPlanName() == null) ? 0 : getFollowPlanName().hashCode());
        result = prime * result + ((getSectionName() == null) ? 0 : getSectionName().hashCode());
        result = prime * result + ((getCallType() == null) ? 0 : getCallType().hashCode());
        result = prime * result + ((getCallCreaterId() == null) ? 0 : getCallCreaterId().hashCode());
        result = prime * result + ((getCallCreaterName() == null) ? 0 : getCallCreaterName().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getCustomerName() == null) ? 0 : getCustomerName().hashCode());
        result = prime * result + ((getCallCreaterNumber() == null) ? 0 : getCallCreaterNumber().hashCode());
        result = prime * result + ((getCustomerNumber() == null) ? 0 : getCustomerNumber().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateorId() == null) ? 0 : getCreateorId().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }
}