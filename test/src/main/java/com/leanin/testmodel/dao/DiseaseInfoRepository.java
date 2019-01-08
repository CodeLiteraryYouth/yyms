package com.leanin.testmodel.dao;

import com.leanin.domain.vo.DiseaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 持久层与数据库交互
 */
public interface DiseaseInfoRepository extends JpaRepository<DiseaseInfo,String>{
    public DiseaseInfo findByDiseaseNum(String diseaseNum);
}
