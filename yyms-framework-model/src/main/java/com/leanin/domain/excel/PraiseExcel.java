package com.leanin.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * 表扬excel导出
 * @author CPJ.
 * @date 2019/5/15.
 * @time 17:36.
 */
@Data
public class PraiseExcel extends BaseRowModel {

    @ExcelProperty(value = "表扬单号",index = 0)
    private String callBackNum;

    @ExcelProperty(value = "表扬类型",index = 1)
    private String callBackType;

    @ExcelProperty(value = "表扬科室",index = 2)
    private String dept;

    @ExcelProperty(value = "被表扬人",index = 3)
    private String praiseedName;

    @ExcelProperty(value = "表扬来源",index = 4)
    private String source;

    @ExcelProperty(value = "表扬目的",index = 5)
    private String target;

    @ExcelProperty(value = "处理状态",index = 6)
    private String dealState;

    @ExcelProperty(value = "登记时间",index = 7)
    private String createTime;
}
