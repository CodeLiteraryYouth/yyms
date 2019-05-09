package com.leanin.domain.dao;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Administrator
 */
@Data
@ToString
@Entity
@Table(name = "leanin_patient_wx")
public class PatientWxDao {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            //主键

    @Column(name = "id_card")
    private String idCard;      //身份证号

    @Column(name = "open_id")
    private String openId;      //微信唯一标识

    @Column(name = "phone_num")
    private String phoneNum;    //手机号

    @Column(name = "patient_name")
    private String patientName; //患者名称

    @Column(name = "inhos_no")
    private String inhosNo;     //住院号

    @Column(name = "patient_id")
    private String patientId;   //his患者主键

    @Column(name = "ex5")
    private String ex5;

}
