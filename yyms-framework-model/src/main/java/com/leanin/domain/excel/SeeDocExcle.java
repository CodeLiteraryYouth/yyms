package com.leanin.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 就诊患者导出excel
 * @author CPJ.
 * @date 2019/5/14.
 * @time 16:10.
 */
@Data
public class SeeDocExcle extends BaseRowModel implements Serializable {

    @ExcelProperty(value = "登记日期",index = 0)
    private String bookTime;

    @ExcelProperty(value = "姓名",index = 1)
    private String patientName;

    @ExcelProperty(value = "性别",index = 2)
    private String sex;

    @ExcelProperty(value = "操作人",index = 3)
    private String registrationDoc;

    @ExcelProperty(value = "预约医生",index = 4)
    private String bookDoc;

    @ExcelProperty(value = "预约科室",index = 5)
    private String dept;

    @ExcelProperty(value = "是否就诊",index = 6)
    private String status;


}
