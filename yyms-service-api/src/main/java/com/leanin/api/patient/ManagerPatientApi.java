package com.leanin.api.patient;

import com.leanin.domain.response.DataOutResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

/**
 * 患者管理
 */
@Api(value="患者接口",description = "患者管理接口，提供数据模型的管理、查询接口")
public interface ManagerPatientApi {

    @ApiOperation("患者查询接口")
    public DataOutResponse findListByParam(Map paramMap);

    @ApiOperation("就诊患者查询接口")
    public DataOutResponse findOutHosPatientByParam(Map paramMap);
}
