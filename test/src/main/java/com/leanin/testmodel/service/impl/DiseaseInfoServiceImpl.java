package com.leanin.testmodel.service.impl;

import com.leanin.domain.vo.DiseaseInfo;
import com.leanin.testmodel.dao.DiseaseInfoRepository;
import com.leanin.testmodel.service.DiseaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiseaseInfoServiceImpl implements DiseaseInfoService {

    @Autowired
    DiseaseInfoRepository diseaseInfoRepository;

    /**
     * 根据疾病编号查询疾病信息
     * @param diseaseNum
     * @return
     */
    @Override
    public DiseaseInfo findByDiseaseNum(String diseaseNum) {
        return diseaseInfoRepository.findByDiseaseNum(diseaseNum);
    }
}
