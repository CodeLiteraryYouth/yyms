package com.leanin.domain.vo;

import lombok.Data;

@Data
public class DoctorVo {

    private Long doctorId;
    private String doctorName;
    private Long doctorWardId;
    private String wardName;
    private Integer status; //1正在使用  2//删除

}
