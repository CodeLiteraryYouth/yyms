package com.leanin.dto;

import com.leanin.domain.LeaninCallLogInfoDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName CallLogInfoDto
 * @Description 通话记录传输实体类
 * @Author 刘壮
 * @Date 2019/4/9 16:57
 * @ModifyDate 2019/4/9 16:57
 * @Version 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CallLogInfoDto extends LeaninCallLogInfoDao {
        /**
         * 录音id
         */
        private Long audioUpId;
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
         * 开始拨打时间
         */
        private String callStartTime;

        /**
         * 结束拨打时间
         */
        private String callEndTime;

        /**
         * 通话时长
         */
        private String holdingTime;
        }