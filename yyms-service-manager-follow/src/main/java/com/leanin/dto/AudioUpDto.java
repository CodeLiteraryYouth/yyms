package com.leanin.dto;

import com.leanin.domain.LeaninAudioUpDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName AudioUpDto
 * @Description 录音上传实体类
 * @Author 刘壮
 * @Date 2019/4/9 16:54
 * @ModifyDate 2019/4/9 16:54
 * @Version 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AudioUpDto extends LeaninAudioUpDao {
    /**
     * 随访计划id
     */
    private Long followPlanId;
    /**
     * 随访计划名称
     */
    private String followPlanName;
    /**
     * 呼叫人员id
     */
    private Long callCreaterId;
    /**
     * 客户id
     */
    private Long customerId;


        }