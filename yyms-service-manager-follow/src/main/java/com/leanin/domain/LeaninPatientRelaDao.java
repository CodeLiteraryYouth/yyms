package com.leanin.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "leanin_patient_rela")
public class LeaninPatientRelaDao {
    @Id
    @Column(name = "patient_rela_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientRelaId;

    /**
     * 联系人姓名
     */
    @Column(name = "patient_rela_name")
    private String patientRelaName;

    /**
     * 与病人关系
     */
    @Column(name = "patient_rela")
    private String patientRela;

    /**
     * 联系人电话号码
     */
    @Column(name = "patient_rela_phone")
    private String patientRelaPhone;

    /**
     * 病人唯一标识
     */
    @Column(name = "patient_id")
    private String patientId;

    /**
     * 是否删除（Y-是,N-否）
     */
    @Column(name = "is_delete")
    private String isDelete;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人id
     */
    @Column(name = "creator_id")
    private Long creatorId;

    /**
     * @return patient_rela_id
     */
    public Long getPatientRelaId() {
        return patientRelaId;
    }

    /**
     * @param patientRelaId
     */
    public void setPatientRelaId(Long patientRelaId) {
        this.patientRelaId = patientRelaId;
    }

    /**
     * 获取联系人姓名
     *
     * @return patient_rela_name - 联系人姓名
     */
    public String getPatientRelaName() {
        return patientRelaName;
    }

    /**
     * 设置联系人姓名
     *
     * @param patientRelaName 联系人姓名
     */
    public void setPatientRelaName(String patientRelaName) {
        this.patientRelaName = patientRelaName;
    }

    /**
     * 获取与病人关系
     *
     * @return patient_rela - 与病人关系
     */
    public String getPatientRela() {
        return patientRela;
    }

    /**
     * 设置与病人关系
     *
     * @param patientRela 与病人关系
     */
    public void setPatientRela(String patientRela) {
        this.patientRela = patientRela;
    }

    /**
     * 获取联系人电话号码
     *
     * @return patient_rela_phone - 联系人电话号码
     */
    public String getPatientRelaPhone() {
        return patientRelaPhone;
    }

    /**
     * 设置联系人电话号码
     *
     * @param patientRelaPhone 联系人电话号码
     */
    public void setPatientRelaPhone(String patientRelaPhone) {
        this.patientRelaPhone = patientRelaPhone;
    }

    /**
     * 获取病人唯一标识
     *
     * @return patient_id - 病人唯一标识
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * 设置病人唯一标识
     *
     * @param patientId 病人唯一标识
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * 获取是否删除（Y-是,N-否）
     *
     * @return is_delete - 是否删除（Y-是,N-否）
     */
    public String getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除（Y-是,N-否）
     *
     * @param isDelete 是否删除（Y-是,N-否）
     */
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
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
     * @return creator_id - 创建人id
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人id
     *
     * @param creatorId 创建人id
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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
        LeaninPatientRelaDao other = (LeaninPatientRelaDao) that;
        return (this.getPatientRelaId() == null ? other.getPatientRelaId() == null : this.getPatientRelaId().equals(other.getPatientRelaId()))
            && (this.getPatientRelaName() == null ? other.getPatientRelaName() == null : this.getPatientRelaName().equals(other.getPatientRelaName()))
            && (this.getPatientRela() == null ? other.getPatientRela() == null : this.getPatientRela().equals(other.getPatientRela()))
            && (this.getPatientRelaPhone() == null ? other.getPatientRelaPhone() == null : this.getPatientRelaPhone().equals(other.getPatientRelaPhone()))
            && (this.getPatientId() == null ? other.getPatientId() == null : this.getPatientId().equals(other.getPatientId()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreatorId() == null ? other.getCreatorId() == null : this.getCreatorId().equals(other.getCreatorId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPatientRelaId() == null) ? 0 : getPatientRelaId().hashCode());
        result = prime * result + ((getPatientRelaName() == null) ? 0 : getPatientRelaName().hashCode());
        result = prime * result + ((getPatientRela() == null) ? 0 : getPatientRela().hashCode());
        result = prime * result + ((getPatientRelaPhone() == null) ? 0 : getPatientRelaPhone().hashCode());
        result = prime * result + ((getPatientId() == null) ? 0 : getPatientId().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreatorId() == null) ? 0 : getCreatorId().hashCode());
        return result;
    }
}