package com.leanin.testmodel.dao;

import com.leanin.domain.vo.DiseaseInfoVo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 持久层与数据库交互
 */
public interface DiseaseInfoRepository extends JpaRepository<DiseaseInfoVo,String>{
    public DiseaseInfoVo findByDiseaseNum(String diseaseNum);
}
