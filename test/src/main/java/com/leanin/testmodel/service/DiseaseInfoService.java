package com.leanin.testmodel.service;

import com.leanin.domain.vo.DiseaseInfo;

/**
 * 疾病表逻辑处理
 */
public interface DiseaseInfoService {
    /**
     * 根据疾病编号查询疾病
     * @return
     */
    DiseaseInfo findByDiseaseNum(String diseaseNum);

}
