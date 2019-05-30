package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.PatientInfoVo;
import com.leanin.exception.CustomException;
import com.leanin.exception.ExceptionCast;
import com.leanin.mapper.PatientInfoMapper;
import com.leanin.model.response.CommonCode;
import com.leanin.service.PatientInfoService;
import com.leanin.utils.CompareUtil;
import com.leanin.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class PatientInfoServiceImpl implements PatientInfoService {

    @Autowired
    private PatientInfoMapper patientInfoMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse addPatientInfo(PatientInfoVo patientInfo) {
        log.info("新增的病人信息为:" + JSON.toJSONString(patientInfo));
        if (patientInfo.getPatientInfoId() == null) {
            return ReturnFomart.retParam(4000, "patientInfoId参数为空！");
        }
		/*String areaCode=stringRedisTemplate.opsForValue().get("areaCode");
		log.info("院区编码为:"+areaCode);*/
        PatientInfoVo patient = patientInfoMapper.findPatientById(patientInfo.getPatientInfoId(), null);
        if (CompareUtil.isNotEmpty(patient)) {
            //更新
            patientInfoMapper.updatePatientInfo(patientInfo);
        } else {
            //插入
            //插入创建时间
            patientInfo.setCreateTime(new Date());
            patientInfoMapper.addPatientInfo(patientInfo);
        }
        return ReturnFomart.retParam(200, patientInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updatePatientInfo(PatientInfoVo patientInfo) {
        log.info("修改的病人信息为:" + JSON.toJSONString(patientInfo));
        if (patientInfo.getPatientInfoId() == null || "".equals(patientInfo.getPatientInfoId())) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        PatientInfoVo patientById = patientInfoMapper.findPatientById(patientInfo.getPatientInfoId(), null);
        if (patientById == null) {//判断本地数据库中是否有 患者信息 有则修改  无则添加
            patientInfoMapper.addPatientInfo(patientInfo);
        } else {
            patientInfoMapper.updatePatientInfo(patientInfo);
        }

        return ReturnFomart.retParam(200, patientInfo);
    }

    @Override
    public DataOutResponse findPatientInfoList(String patientName, String patientId, String beginDate, String endDate) {
        log.info("病人的姓名为:" + patientName + "-" + "建档编号为:" + patientId + "-" + "建档起始时间:" + beginDate + "-" + "建档结束时间:" + endDate);
        String areaCode = stringRedisTemplate.opsForValue().get("areaCode");
        log.info("院区编码为:" + areaCode);
        List<PatientInfoVo> patientInfo = patientInfoMapper.findPatientInfoList(patientName, areaCode, patientId, beginDate, endDate);
        return ReturnFomart.retParam(200, patientInfo);
    }

    @Override
    public DataOutResponse findPatientById(String patientId) {
        String patientInfo = stringRedisTemplate.opsForValue().get("patient_" + patientId);
        PatientInfoVo patient = null;
        if (StringUtils.isEmpty(patientInfo)) {
            String areaCode = stringRedisTemplate.opsForValue().get("areaCode");
            log.info("院区编码为:" + areaCode);
            patient = patientInfoMapper.findPatientById(patientId, areaCode);
            log.info("查询的病人信息为:" + JSON.toJSONString(patient));
            stringRedisTemplate.opsForValue().set("patient_" + patientId, JSON.toJSONString(patient));
        } else {
            patient = JsonUtil.json2Obj(patientInfo, PatientInfoVo.class);
        }
        return ReturnFomart.retParam(200, patient);
    }

    @Override
    public DataOutResponse findByParam(Integer page, Integer pageSize, String hosNo, String idCard, Integer patientSource, String patientName) {
        log.info("条件查询患者档案传递的参数 -->" + "当前页:" + page + "每页展示的条数:" + pageSize + "患者号码:" + hosNo + "患者身份证号" + idCard + "患者的来源" + patientSource);
        if (page == null || page < 0) {
            page = 1;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 10;
        }
        PageHelper.startPage(page, pageSize);
        Page<PatientInfoVo> pageList = (Page<PatientInfoVo>) patientInfoMapper.findByParam(hosNo, idCard, patientSource, patientName);
        Map result = new HashMap();
        result.put("totalCount", pageList.getTotal());
        result.put("list", pageList.getResult());
        return ReturnFomart.retParam(200, result);
    }

    @Override
    public DataOutResponse findById(Long id) {
        log.info("查询患者档案的id是" + id);
        PatientInfoVo patientInfoVo = patientInfoMapper.findById(id);
        if (patientInfoVo == null) {
            return ReturnFomart.retParam(5400, id);
        }
        return ReturnFomart.retParam(200, patientInfoVo);
    }

    @Override
    public DataOutResponse updatePatienInfo(PatientInfoVo patientInfoVo) {
        log.info("修改的患者档案信息:"+patientInfoVo.toString());
        PatientInfoVo byId = patientInfoMapper.findById(patientInfoVo.getId());
        if (byId == null) {
            return ReturnFomart.retParam(5400, patientInfoVo);

        }
        patientInfoMapper.updateById(patientInfoVo);
        return ReturnFomart.retParam(200,"患者档案修改成功");
    }

    @Override
    public DataOutResponse findByIdAndSource(String patientId, Integer patientSource) {
        PatientInfoVo byPatientIdAndSource = patientInfoMapper.findByPatientIdAndSource(patientId, patientSource);
        if (byPatientIdAndSource == null){
            return ReturnFomart.retParam(5400,patientId);
        }
        return ReturnFomart.retParam(200,byPatientIdAndSource);
    }

}
