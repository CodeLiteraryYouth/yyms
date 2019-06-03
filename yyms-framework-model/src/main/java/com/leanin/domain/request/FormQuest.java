package com.leanin.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author CPJ.
 * @date 2019/6/3.
 * @time 14:08.
 */
@Data
public class FormQuest implements Serializable {

    private String formNum;

    private String planNum;

    private String optionContent;
}
