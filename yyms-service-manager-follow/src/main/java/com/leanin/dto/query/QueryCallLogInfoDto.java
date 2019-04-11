package com.leanin.dto.query;

import com.leanin.query.QueryPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import java.util.Date;

/**
 * @ClassName QueryCallLogInfoDto
 * @Description 查询条件
 * @Author 刘壮
 * @Date 2019/4/10 10:48
 * @ModifyDate 2019/4/10 10:48
 * @Version 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QueryCallLogInfoDto extends QueryPage {
        /**
         * 开始时间
         */
        private Date startTime;
        /**
         * 结束时间
         */
        private Date endTime;
        /**
         * 随访计划id
         */
        private Long followPlanId;

        /**
         * 科室id
         */
        private String sectionId;
        /**
         * 客户姓名
         */
        private String customerName;
        }