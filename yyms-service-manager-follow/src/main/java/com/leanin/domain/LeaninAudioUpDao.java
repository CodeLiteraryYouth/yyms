package com.leanin.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "leanin_audio_up")
public class LeaninAudioUpDao {
    /**
     * 录音id
     */
    @Id
    @Column(name = "audio_up_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long audioUpId;

    /**
     * 录音来源类型(0,1,2)
     */
    @Column(name = "audio_source")
    private Integer audioSource;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件格式
     */
    private String format;

    /**
     * 音频时长(秒)
     */
    private Integer duration;

    @Column(name = "create_time")
    private Date createTime;

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
    @Column(name = "holding_time")
    private String holdingTime;

    /**
     * 获取录音id
     *
     * @return audio_up_id - 录音id
     */
    public Long getAudioUpId() {
        return audioUpId;
    }

    /**
     * 设置录音id
     *
     * @param audioUpId 录音id
     */
    public void setAudioUpId(Long audioUpId) {
        this.audioUpId = audioUpId;
    }

    /**
     * 获取录音来源类型(0,1,2)
     *
     * @return audio_source - 录音来源类型(0,1,2)
     */
    public Integer getAudioSource() {
        return audioSource;
    }

    /**
     * 设置录音来源类型(0,1,2)
     *
     * @param audioSource 录音来源类型(0,1,2)
     */
    public void setAudioSource(Integer audioSource) {
        this.audioSource = audioSource;
    }

    /**
     * 获取文件路径
     *
     * @return path - 文件路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置文件路径
     *
     * @param path 文件路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取文件格式
     *
     * @return format - 文件格式
     */
    public String getFormat() {
        return format;
    }

    /**
     * 设置文件格式
     *
     * @param format 文件格式
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * 获取音频时长(秒)
     *
     * @return duration - 音频时长(秒)
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * 设置音频时长(秒)
     *
     * @param duration 音频时长(秒)
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取开始拨打时间
     *
     * @return call_start_time - 开始拨打时间
     */
    public Date getCallStartTime() {
        return callStartTime;
    }

    /**
     * 设置开始拨打时间
     *
     * @param callStartTime 开始拨打时间
     */
    public void setCallStartTime(Date callStartTime) {
        this.callStartTime = callStartTime;
    }

    /**
     * 获取结束拨打时间
     *
     * @return call_end_time - 结束拨打时间
     */
    public Date getCallEndTime() {
        return callEndTime;
    }

    /**
     * 设置结束拨打时间
     *
     * @param callEndTime 结束拨打时间
     */
    public void setCallEndTime(Date callEndTime) {
        this.callEndTime = callEndTime;
    }

    /**
     * 获取通话时长
     *
     * @return holding_time - 通话时长
     */
    public String getHoldingTime() {
        return holdingTime;
    }

    /**
     * 设置通话时长
     *
     * @param holdingTime 通话时长
     */
    public void setHoldingTime(String holdingTime) {
        this.holdingTime = holdingTime;
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
        LeaninAudioUpDao other = (LeaninAudioUpDao) that;
        return (this.getAudioUpId() == null ? other.getAudioUpId() == null : this.getAudioUpId().equals(other.getAudioUpId()))
            && (this.getAudioSource() == null ? other.getAudioSource() == null : this.getAudioSource().equals(other.getAudioSource()))
            && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
            && (this.getFormat() == null ? other.getFormat() == null : this.getFormat().equals(other.getFormat()))
            && (this.getDuration() == null ? other.getDuration() == null : this.getDuration().equals(other.getDuration()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCallStartTime() == null ? other.getCallStartTime() == null : this.getCallStartTime().equals(other.getCallStartTime()))
            && (this.getCallEndTime() == null ? other.getCallEndTime() == null : this.getCallEndTime().equals(other.getCallEndTime()))
            && (this.getHoldingTime() == null ? other.getHoldingTime() == null : this.getHoldingTime().equals(other.getHoldingTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAudioUpId() == null) ? 0 : getAudioUpId().hashCode());
        result = prime * result + ((getAudioSource() == null) ? 0 : getAudioSource().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getFormat() == null) ? 0 : getFormat().hashCode());
        result = prime * result + ((getDuration() == null) ? 0 : getDuration().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCallStartTime() == null) ? 0 : getCallStartTime().hashCode());
        result = prime * result + ((getCallEndTime() == null) ? 0 : getCallEndTime().hashCode());
        result = prime * result + ((getHoldingTime() == null) ? 0 : getHoldingTime().hashCode());
        return result;
    }
}