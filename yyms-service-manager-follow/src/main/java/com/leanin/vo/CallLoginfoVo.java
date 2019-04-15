package com.leanin.vo;

import com.leanin.domain.LeaninCallLogInfoDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName CallLoginfoVo
 * @Description TODO
 * @Author 刘壮
 * @Date 2019/4/10 11:35
 * @ModifyDate 2019/4/10 11:35
 * @Version 1.0
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CallLoginfoVo extends LeaninCallLogInfoDao {
    /**
     * 通话号码
     */
    private String phoneNumber;
    /**
     * 拨打时间
     */
    private String phoneTime;
        }