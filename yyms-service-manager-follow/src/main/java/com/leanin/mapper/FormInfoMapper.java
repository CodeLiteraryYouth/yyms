package com.leanin.mapper;

import com.leanin.domain.dto.CommonFormInfoDto;
import com.leanin.domain.vo.FormInfoExt;
import com.leanin.domain.vo.FormInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FormInfoMapper {

	/**
	 * 查询共享表单信息
	 * @param formType
	 * @param formName
	 * @return
	 */
	List<CommonFormInfoDto> findCommonForm(@Param("formType") Integer formType, @Param("formName") String formName,@Param("shareStatus")Integer shareStatus);

	/**
	 * 根绝分类ID查询表单信息
	 * @param formTypeId
	 * @return
	 */
	List<FormInfoVo> findFormListByTypeId(@Param("formTypeId") Long formTypeId, @Param("formName") String formName);

	/**
	 * 根据病区编码查询表单列表信息
	 * @param wardCode
	 * @param formName
	 * @param formType
	 * @return
	 */
	List<FormInfoVo> findFormListByWardCode(@Param("wardCode") String wardCode, @Param("formName") String formName, @Param("formType") Integer formType);

	/**
	 * 根据单号搜索
	 * @param formType
	 * @return
	 */
	List<FormInfoVo> findFormList(@Param("formName") String formName, @Param("formType") Integer formType,@Param("formFormId") Integer formFormId,@Param("shareStatus") Integer shareStatus);
	/**
	 * 修改表单状态
	 * @param formNum
	 * @return
	 */
    int updateFormStatus(@Param("formNum") String formNum, @Param("formStatus") Integer formStatus);

    /**
     * 新增表单信息
     * @param record
     * @return
     */
    int addFormInfo(FormInfoVo record);

    /**
     * 根绝表单号查询表单信息
     * @param formNum
     * @return
     */
	FormInfoVo findFormInfoById(@Param("formNum") String formNum);
    
    /**
     * 根据表单名字查询表单信息
     * @param formName
     * @return
     */
	FormInfoVo findFormInfoByName(@Param("formName") String formName);

    /**
     * 修改表单信息
     * @param record
     * @return
     */
    int updateFormInfo(FormInfoVo record);



	List<FormInfoExt> findFormListByOpenid(@Param("openid") String openid, @Param("followStatus") Integer followStatus, @Param("planType") Integer planType);

	List<FormInfoExt> findFormListByOpenidExt(@Param("openid")String openid,@Param("followStatus") Integer followStatus,@Param("planType") Integer planType);

    FormInfoVo findFormInfoByIdAndStatus(@Param("formNum") String formNum,@Param("formStatus") Integer formStatus,@Param("planPatientId")Long planPatientId );
}