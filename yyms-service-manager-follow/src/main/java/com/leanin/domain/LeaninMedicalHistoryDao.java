package com.leanin.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "leanin_medical_history")
public class LeaninMedicalHistoryDao {
    /**
     * 主键id
     */
    @Id
    @Column(name = "medical_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicalHistoryId;

    /**
     * 患者id
     */
    @Column(name = "patient_info_id")
    private String patientInfoId;

    /**
     * 疾病类型（0-既往史,1-家族史,2-过敏史,3-手术史）
     */
    @Column(name = "medical_type")
    private Integer medicalType;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建idi
     */
    @Column(name = "creator_id")
    private Long creatorId;

    /**
     * 疾病描述
     */
    @Column(name = "medical_description")
    private String medicalDescription;

    /**
     * 是否删除（Y-是，N-否）
     */
    @Column(name = "is_delete")
    private String isDelete;

    /**
     * 获取主键id
     *
     * @return medical_history_id - 主键id
     */
    public Long getMedicalHistoryId() {
        return medicalHistoryId;
    }

    /**
     * 设置主键id
     *
     * @param medicalHistoryId 主键id
     */
    public void setMedicalHistoryId(Long medicalHistoryId) {
        this.medicalHistoryId = medicalHistoryId;
    }

    /**
     * 获取患者id
     *
     * @return patient_info_id - 患者id
     */
    public String getPatientInfoId() {
        return patientInfoId;
    }

    /**
     * 设置患者id
     *
     * @param patientInfoId 患者id
     */
    public void setPatientInfoId(String patientInfoId) {
        this.patientInfoId = patientInfoId;
    }

    /**
     * 获取疾病类型（0-既往史,1-家族史,2-过敏史,3-手术史）
     *
     * @return medical_type - 疾病类型（0-既往史,1-家族史,2-过敏史,3-手术史）
     */
    public Integer getMedicalType() {
        return medicalType;
    }

    /**
     * 设置疾病类型（0-既往史,1-家族史,2-过敏史,3-手术史）
     *
     * @param medicalType 疾病类型（0-既往史,1-家族史,2-过敏史,3-手术史）
     */
    public void setMedicalType(Integer medicalType) {
        this.medicalType = medicalType;
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
     * 获取创建idi
     *
     * @return creator_id - 创建idi
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建idi
     *
     * @param creatorId 创建idi
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取疾病描述
     *
     * @return medical_description - 疾病描述
     */
    public String getMedicalDescription() {
        return medicalDescription;
    }

    /**
     * 设置疾病描述
     *
     * @param medicalDescription 疾病描述
     */
    public void setMedicalDescription(String medicalDescription) {
        this.medicalDescription = medicalDescription;
    }

    /**
     * 获取是否删除（Y-是，N-否）
     *
     * @return is_delete - 是否删除（Y-是，N-否）
     */
    public String getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除（Y-是，N-否）
     *
     * @param isDelete 是否删除（Y-是，N-否）
     */
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
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
        LeaninMedicalHistoryDao other = (LeaninMedicalHistoryDao) that;
        return (this.getMedicalHistoryId() == null ? other.getMedicalHistoryId() == null : this.getMedicalHistoryId().equals(other.getMedicalHistoryId()))
            && (this.getPatientInfoId() == null ? other.getPatientInfoId() == null : this.getPatientInfoId().equals(other.getPatientInfoId()))
            && (this.getMedicalType() == null ? other.getMedicalType() == null : this.getMedicalType().equals(other.getMedicalType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreatorId() == null ? other.getCreatorId() == null : this.getCreatorId().equals(other.getCreatorId()))
            && (this.getMedicalDescription() == null ? other.getMedicalDescription() == null : this.getMedicalDescription().equals(other.getMedicalDescription()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMedicalHistoryId() == null) ? 0 : getMedicalHistoryId().hashCode());
        result = prime * result + ((getPatientInfoId() == null) ? 0 : getPatientInfoId().hashCode());
        result = prime * result + ((getMedicalType() == null) ? 0 : getMedicalType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreatorId() == null) ? 0 : getCreatorId().hashCode());
        result = prime * result + ((getMedicalDescription() == null) ? 0 : getMedicalDescription().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }
}