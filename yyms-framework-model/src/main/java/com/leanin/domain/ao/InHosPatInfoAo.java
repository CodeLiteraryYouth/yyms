package com.leanin.domain.ao;

import com.leanin.domain.vo.InHosPatInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InHosPatInfoAo extends InHosPatInfo {

    private List<String> diagnosisNameList;//诊断名称集合
}
