package com.leanin.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName QueryPage
 * @Description 基础分页
 * @Author 刘壮
 * @Date 2019/4/10 10:52
 * @ModifyDate 2019/4/10 10:52
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryPage{
    /**
     * 当前页
     */
    private int     currentPage    = 1;
    /**
     * 当前页显示数量
     */
    private int     pageSize   = 10;

    private boolean isNeedPage = true;




        }