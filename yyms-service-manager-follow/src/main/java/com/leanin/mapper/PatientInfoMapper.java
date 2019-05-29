package com.leanin.mapper;

import com.leanin.domain.dto.PatientInfoDto;
import com.leanin.domain.vo.PatientInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PatientInfoMapper {

    /**
     * 根据搜索条件查询病人信息
     * @return
     */
    List<PatientInfoDto> findPatientByParam(@Param("sex") Integer sex, @Param("startAge") Integer startAge, @Param("endAge") Integer endAge, @Param("isPhone") Integer isPhone);
	/**
	 * 根绝关注病人的姓名进行模糊搜索
	 * @param patientName
	 * @return
	 */
	List<PatientInfoVo> findPatientInfoList(@Param("patientName") String patientName, @Param("areaCode") String areaCode,
                                            @Param("patientId") String patientId, @Param("beginDate") String beginDate,
                                            @Param("endDate") String endDate);

    /**
     * 增加建档病人信息
     * @param record
     * @return
     */
    int addPatientInfo(PatientInfoVo record);

    /**
     * 根绝ID查询病人信息
     * @param patientInfoId
     * @return
     */
    PatientInfoVo findPatientById(@Param("patientInfoId") String patientInfoId, @Param("areaCode") String areaCode);

    /**
     * 修改建档病人信息
     * @param record
     * @return
     */
    int updatePatientInfo(PatientInfoVo record);

    List<PatientInfoVo> findByParam(@Param("hosNo") String hosNo,@Param("idCard") String idCard,@Param("patientSource") Integer patientSource,@Param("patientName") String patientName);

    PatientInfoVo findById(@Param("id") Long id);

    void updateById(PatientInfoVo patientInfoVo);

}