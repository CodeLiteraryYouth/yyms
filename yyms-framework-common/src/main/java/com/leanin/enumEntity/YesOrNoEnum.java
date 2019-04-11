package com.leanin.enumEntity;

/**
 * @ClassName YesOrNoEnum
 * @Description 是否删除状态枚举
 * @Author 刘壮
 * @Date 2019/4/9 17:33
 * @ModifyDate 2019/4/9 17:33
 * @Version 1.0
 */
public enum  YesOrNoEnum{
    YES("Y", "是"),
    NO("N","否"),
            ;
    private final String code;
    private final String value;
    private YesOrNoEnum(String code,String value){
        this.code =code;
        this.value = value;
    }
    public String getCode() {
        return code;
    }
    public String getValue() {
        return value;
    }
        }