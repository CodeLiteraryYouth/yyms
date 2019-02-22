package com.leanin.oauth.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.DiseaseInfoVo;
import com.leanin.mapper.DataDictionaryMapper;
import com.leanin.oauth.service.DataDictoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DataDictoryServiceImpl implements DataDictoryService {

	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;
	
	
	@Override
	public DataOutResponse findDiseaseList(int page, int pageSize, String diseaseName) {
		log.info("搜索的疾病名称为:"+diseaseName);
		PageHelper.startPage(page, pageSize);
		List<DiseaseInfoVo>  diseaseList=dataDictionaryMapper.findDiseaseList(diseaseName);
		PageInfo pageInfo=new PageInfo<>(diseaseList);
		return ReturnFomart.retParam(200, pageInfo);
	}

}
