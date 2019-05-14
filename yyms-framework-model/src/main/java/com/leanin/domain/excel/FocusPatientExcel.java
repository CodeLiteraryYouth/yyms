package com.leanin.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 关注患者导入excel对象
 * @author CPJ.
 * @date 2019/5/14.
 * @time 11:28.
 */
@Data
public class FocusPatientExcel extends BaseRowModel implements Serializable {

    @ExcelProperty(value = "姓名",index = 0)
    private String patientName;         //患者姓名

    @ExcelProperty(value = "科室",index = 1)
    private String dept;                //科室

    @ExcelProperty(value = "住院号",index = 2)
    private String inHosNo;             //住院号

    @ExcelProperty(value = "性别",index = 3)
    private String sex;                 //性别

    @ExcelProperty(value = "出生日期",index = 4,format = "yyyy-MM-dd")
    private String birthday;              //出生日期

    @ExcelProperty(value = "联系方式",index = 5)
    private String phoneNum;            //联系方式

    @ExcelProperty(value = "身份证号",index = 6)
    private String idCard;              //身份证号

    @ExcelProperty(value = "患者来源",index = 7)
    private String patientSource;       //患者来源




}
