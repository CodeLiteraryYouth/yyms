package com.leanin.api.patient;

import com.leanin.domain.response.ReturnFomart;
import io.swagger.annotations.Api;

/**
 * 患者管理
 */
@Api(value="患者接口",description = "患者管理接口，提供数据模型的管理、查询接口")
public interface ManagerPatientApi {

    public ReturnFomart findListByParam();
}
