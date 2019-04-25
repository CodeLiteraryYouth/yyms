package com.leanin.service;

import com.leanin.domain.LeaninMedicalHistoryDao;
import com.leanin.domain.response.DataOutResponse;

/**
 * 疾病信息
 * @author zliu
 *
 */
public interface MedicalHistoryService {
	/**
	 * 根据患者唯一id查出病史信息
	 * @param patientId
	 * @return
	 */
	DataOutResponse listPage(String patientId);
	/**
	 * 根据类型新增
	 * @param leaninMedicalHistoryDao
	 * @return
	 */
	DataOutResponse addByType(LeaninMedicalHistoryDao leaninMedicalHistoryDao);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	DataOutResponse selectByid(Long id);
	/**
	 * 更新
	 * @param leaninMedicalHistoryDao
	 * @return
	 */
	DataOutResponse updateEntity(LeaninMedicalHistoryDao leaninMedicalHistoryDao);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	DataOutResponse deleteByIds(String ids);

}
