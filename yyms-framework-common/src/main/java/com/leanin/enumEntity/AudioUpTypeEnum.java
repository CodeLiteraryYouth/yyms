package com.leanin.enumEntity;

/**
 * @ClassName YesOrNoEnum
 * @Description 录音状态枚举
 * @Author 刘壮
 * @Date 2019/4/9 17:33
 * @ModifyDate 2019/4/9 17:33
 * @Version 1.0
 */
public enum AudioUpTypeEnum {
    CALL(0, "打电话录音"),
            ;
    private final Integer code;
    private final String value;
    private AudioUpTypeEnum(Integer code, String value){
        this.code =code;
        this.value = value;
    }
    public Integer getCode() {
        return code;
    }
    public String getValue() {
        return value;
    }
        }