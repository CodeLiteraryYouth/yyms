package com.leanin.service;


import com.leanin.domain.response.DataOutResponse;

public interface DataDictoryService {

	/**
	 * 查询疾病列表信息
	 * @param page
	 * @param pageSize
	 * @param diseaseName
	 * @return
	 */
	DataOutResponse findDiseaseList(int page, int pageSize, String diseaseName);
}
