package com.leanin.enumEntity;

/**
 * 疾病类型枚举
 * @author zliu
 *
 */
public enum MedicalHistoryTypeEnum {
    PASH(0, "既往史"),
    FAMILY(1,"家族史"),
    ALLERGY(2,"过敏史"),
    OPERATION(3,"手术史"),
            ;
    private final Integer code;
    private final String value;
    private MedicalHistoryTypeEnum(Integer code, String value){
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