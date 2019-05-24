package com.leanin.service;

import com.leanin.domain.LeaninPatientRelaDao;
import com.leanin.domain.response.DataOutResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author zliu
 *
 */
public interface LeaninPatientRelaService {
	/**
	 * 查询
	 * @param patientId
	 * @return
	 */
	DataOutResponse listPage(String patientId);
	/**
	 * 新增
	 * @param leaninPatientRelaDao
	 * @return
	 */
	DataOutResponse add(LeaninPatientRelaDao leaninPatientRelaDao, HttpServletRequest request);
	/**
	 * 根据id查看详情
	 * @param id
	 * @return
	 */
	DataOutResponse findById(Long id);
	/**
	 * 修改
	 * @param leaninPatientRelaDao
	 * @return
	 */
	DataOutResponse update(LeaninPatientRelaDao leaninPatientRelaDao);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	DataOutResponse deleteByIds(String ids);

}
