package com.leanin.domain.ao;

import com.leanin.domain.vo.OutHosPatInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OutHosPatInfoAo extends OutHosPatInfo {

    private List<String> diagnosisNameList;
}
