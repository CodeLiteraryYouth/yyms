package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.FormInfoVo;

/**
 * 表单信息Service
 * @author Administrator
 *
 */
public interface FormInfoService {

	/**
	 * 查询共享表单信息
	 * @param formType
	 * @param formName
	 * @return
	 */
	DataOutResponse findCommonForm(Integer page, Integer pageSize, Integer formType, String formName,Integer shareStatus);
	/**
	 * 根据表单名字搜索
	 * @param formType
	 * @return
	 */
	DataOutResponse findFormList(Integer page, Integer pageSize, String formName, Integer formType,Integer formFormId,Integer shareStatus);

	/**
	 * 根绝分类ID查询表单信息
	 * @param formTypeId
	 * @return
	 */
	DataOutResponse findFormListByTypeId(Integer page, Integer pageSize, Long formTypeId, String formName);

	/**
	 * 根据病区编码查询表单列表信息
	 * @param wardCode
	 * @param formName
	 * @param formType
	 * @return
	 */
	DataOutResponse findFormListByWardCode(Integer page, Integer pageSize, String wardCode, String formName, Integer formType);
	/**
	 * 修改表单状态
	 * @param formNum
	 * @return
	 */
	DataOutResponse updateFormStatus(String formNum, Integer formStatus);

    /**
     * 新增表单信息
     * @param record
     * @return
     */
	DataOutResponse addFormInfo(FormInfoVo record);

    /**
     * 根绝表单号查询表单信息
     * @param formNum
     * @return
     */
	DataOutResponse findFormInfoById(String formNum);

    /**
     * 修改表单信息
     * @param record
     * @return
     */
	DataOutResponse updateFormInfo(FormInfoVo record);

	DataOutResponse findFormListByOpenid(String openid,Integer followStatus,Integer planType,Integer formStatus);

	DataOutResponse shareForm(String forNum, Integer status);

    DataOutResponse findEduFormByIdCard(String idCard, Integer page, Integer pageSize);
}
