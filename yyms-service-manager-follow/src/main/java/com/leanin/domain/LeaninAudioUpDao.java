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
    /**
     * 来电唯一标识
     */
    @Column(name = "call_uuid")
    private String callUuid;

    @Column(name = "create_time")
    private Date createTime;

    

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

   

    
    

    public String getCallUuid() {
		return callUuid;
	}

	public void setCallUuid(String callUuid) {
		this.callUuid = callUuid;
	}


}