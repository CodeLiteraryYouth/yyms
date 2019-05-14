package com.leanin.api.patient;

import com.leanin.domain.response.DataOutResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

/**
 * 患者管理
 */
@Api(value="患者接口",description = "患者管理接口，提供数据模型的管理、查询接口")
public interface ManagerPatientApi {

    @ApiOperation("患者查询接口")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "paramMap", value = "查询信息",required = true,dataType = "map"))
    public DataOutResponse findListByParam(Map paramMap);

    @ApiOperation("就诊患者查询接口")
    public DataOutResponse findOutHosPatientByParam(Map paramMap);

    @ApiOperation("给随访提供接口，门诊病人信息")
    public Map findOutHosPatientByParamToSF(Map paramMap);

    @ApiOperation("给随访提供接口，出住院病人信息")
    public Map findInHosPatientByParamToSF(Map paramMap);

    @ApiOperation("给随访提供接口，根据病人id查询 患者信息")
    public Map findInHosPatientById(String patientId);

    @ApiOperation("给随访提供接口，根据病人id查询 出住院记录")
    public List<Map> findInHosRecordById(Map paraMap);

    @ApiOperation("给随访提供接口，根据病人id查询 患者信息")
    public Map findOutHosPatientById(String patientId);

    @ApiOperation("给随访提供接口，根据病人id查询 门诊记录")
    public List<Map> findOutHosRecordById(String patientId);


    @ApiOperation("查询预约医生列表")
    public DataOutResponse findRegList(Map parmMap);

    @ApiOperation("增加医生列表已预约人数")
    public DataOutResponse updateRegnum(Map paramMap);

    @ApiOperation("增加预约列表信息")
    public DataOutResponse updateOrderList(Map paramMap);

    @ApiOperation("查询医生的科室列表")
    public DataOutResponse findDoctorDept();

    @ApiOperation("取消预约")
    public DataOutResponse cancelOrder(Map paramMap);

    @ApiOperation("根据身份证号查询患者信息")
    public DataOutResponse findByIdCard(String idCard,String patientName);

}
