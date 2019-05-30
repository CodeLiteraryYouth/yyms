package com.leanin.mapper;

import com.leanin.domain.excel.FocusPatientExcel;
import com.leanin.domain.vo.FocusPatientVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FocusPatientMapper {
	
	/**
	 * 查询关注患者信息列表
	 * @param patientName
	 * @return
	 */
	List<FocusPatientVo> findPatientList(@Param("patientName") String patientName,@Param("userId") Long userId,
										 @Param("healthCardNo") String healthCardNo,@Param("idCard") String idCard,
										 @Param("patientSource") Integer patientSource);
	
	/**
	 * 修改病人的状态信息
	 * @param focusId
	 * @param status
	 * @return
	 */
    int updatePatientStatus(@Param("focusId") Long focusId, @Param("status") Integer status);

    /**
     * 保存关注患者的信息
     * @param record
     * @return
     */
    int insertFocusPatient(FocusPatientVo record);

    /**
     * 查询单独的病人信息
     * @param patientId
     * @return
     */
	FocusPatientVo selectFocusPatientById(@Param("patientId") String patientId,@Param("userId")Long userId);

    FocusPatientVo findPatientByFocusId(@Param("focusId") Long focusId);

    List<FocusPatientExcel> exportExcel(@Param("patientName") String patientName);
}