package com.leanin.domain.dao;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Entity
@Table(name = "leanin_admin_user")
public class UserDao implements Serializable {

    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;	//管理员ID

    @Column(name = "admin_name")
    private String adminName;	//管理员名称

    @Column(name = "admin_work_num")
    private String workNum;	//用户工号

    @Column(name = "admin_password")
    private String password;	//密码

    @Column(name = "admin_ward_code")
    private String wardCode;	//用户科室编码

    @Column(name = "admin_job_title")
    private String jobTitle;	//用户职称

    @Column(name = "admin_desc")
    private String adminDesc;	//用户自我介绍

    @Column(name = "admin_phone")
    private String phone;	//用户手机号

    @Column(name = "area_code")
    private String areaCode;	//院区编码

    @Column(name = "admin_id_card")
    private String idCard;	//身份证号

    @Column(name = "admin_sex")
    private Integer sex;	//性别  1男 2女

    @Column(name = "admin_birthday")
    private Date birthday;	//管理员出生日期

    @Column(name = "organ_attri")
    private String organAscri;	//机构归属

    @Column(name = "admin_state")
    private Integer adminState;	//用户状态(-1:已注销 0：正在使用)

    @Column(name = "remark")
    private String remark;	//备注

    @Column(name = "hospital_name")
    private String hospitalName;	//医院名称

    @Column(name = "user_type")
    private Integer userType;		//用户类型

    @Column(name = "menu")
    private String menu;            //权限字符串

    @Column(name = "last_login_time")
    private Date lastLoginTime;     //上次登录时间
}
