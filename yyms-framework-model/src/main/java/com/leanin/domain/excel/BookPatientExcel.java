package com.leanin.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 预约患者excel对象
 * @author CPJ.
 * @date 2019/5/14.
 * @time 14:53.
 */
@Data
public class BookPatientExcel extends BaseRowModel implements Serializable {

    @ExcelProperty(value = "姓名",index = 0)
    private String patientName;

    @ExcelProperty(value = "性别",index = 1)
    private String sex;

    @ExcelProperty(value = "身份证号",index = 2)
    private String idCard;

    @ExcelProperty(value = "手机号",index = 3)
    private String phoneNum;

    @ExcelProperty(value = "预约医生",index = 4)
    private String bookDoc;

    @ExcelProperty(value = "预约科室",index = 5)
    private String bookDept;

    @ExcelProperty(value = "预约就诊时间",index = 6)
    private String seeDocTime;

    @ExcelProperty(value = "预约时间",index = 7)
    private String bookTime;

    @ExcelProperty(value = "预约来源",index = 8)
    private String bookSource;
}
