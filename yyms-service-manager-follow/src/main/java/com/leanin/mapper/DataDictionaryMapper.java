package com.leanin.mapper;

import com.leanin.domain.vo.DiseaseInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据字典的Mapper
 * @author Administrator
 *
 */
public interface DataDictionaryMapper {

	/**
	 * 查询疾病列表
	 * @param diseaseName
	 * @return
	 */
	List<DiseaseInfoVo> findDiseaseList(@Param("diseaseName") String diseaseName);
}
