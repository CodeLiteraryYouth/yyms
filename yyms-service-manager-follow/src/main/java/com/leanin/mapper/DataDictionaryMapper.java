package com.leanin.mapper;

import com.leanin.domain.vo.DiseaseInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataDictionaryMapper {

	/**
	 *
	 * @param diseaseName
	 * @return
	 */
	List<DiseaseInfoVo> findDiseaseList(@Param("diseaseName") String diseaseName);
}
