package com.leanin.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.excel.FocusPatientExcel;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.FocusPatientVo;
import com.leanin.mapper.FocusPatientMapper;
import com.leanin.service.FocusPatientService;
import com.leanin.utils.CompareUtil;
import com.leanin.utils.JsonUtil;
import com.leanin.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FocusPatientServiceImpl implements FocusPatientService {

	@Autowired
	private FocusPatientMapper focusPatientMapper;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Override
	public DataOutResponse findPatientList(String patientName,Long userId,Integer page,Integer pageSize,
										   String healthCardNo,String idCard,Integer patientSource) {
		log.info("查询的病人姓名为:"+patientName);
//		String patientJson=redisTemplate.opsForValue().get("patient_"+patientName);
//		if(StringUtils.isEmpty(patientJson)) {
		if (page == null || page < 0){
			page = 1;
		}
		if (pageSize == null || pageSize < 0){
			pageSize = 10;
		}
		PageHelper.startPage(page,pageSize);
		Page<FocusPatientVo> pageVo= (Page<FocusPatientVo>) focusPatientMapper.findPatientList(patientName,userId,healthCardNo,idCard,patientSource);
			log.info("查询的关注病人列表信息为:"+ JSON.toJSONString(pageVo.getResult()));
//			redisTemplate.opsForValue().set("patient_"+patientName, JSON.toJSONString(patientList));
//		} else {
//			patientList= JsonUtil.json2list(patientJson, FocusPatientVo.class);
//		}
		Map data = new HashMap();
		data.put("totalCount",pageVo.getTotal());
		data.put("list",pageVo.getResult());
		return ReturnFomart.retParam(200, data);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updatePatientStatus(String[] focusIds, Integer status) {
		for (String focusId : focusIds) {
			log.info("关注的病人主键为:"+focusId+"-"+"病人状态为:"+status);
			FocusPatientVo focusPatientVo = focusPatientMapper.findPatientByFocusId(Long.parseLong(focusId));
			if (focusPatientVo == null ){
				return ReturnFomart.retParam(300,"数据不存在");
			}
			focusPatientMapper.updatePatientStatus(Long.parseLong(focusId), status);
		}
		return ReturnFomart.retParam(200, "操作成功");
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse insertFocusPatient(FocusPatientVo record) {
		log.info("新增的关注病人信息为:"+ JSON.toJSONString(record));
		FocusPatientVo patient=focusPatientMapper.selectFocusPatientById(record.getPatientId(),record.getUserId());
		if(CompareUtil.isNotEmpty(patient)) {
			return ReturnFomart.retParam(5000, "病人已关注，不需要再次关注");
		}
		record.setFocusStatus(1);// 1 关注  -1 取消关注
		focusPatientMapper.insertFocusPatient(record);
		return ReturnFomart.retParam(200, record);
	}

	@Override
	public DataOutResponse selectFocusPatientById(String patientId,Long userId) {
		String patientJson=redisTemplate.opsForValue().get("patient_"+patientId);
		FocusPatientVo patient=null;
		if(StringUtils.isEmpty(patientJson)) {
			patient=focusPatientMapper.selectFocusPatientById(patientId,userId);
			log.info("查询的关注病人信息为:"+ JSON.toJSONString(patient));
			redisTemplate.opsForValue().set("patient_"+patientId, JSON.toJSONString(patient));
		} else {
			patient=JsonUtil.json2Obj(patientJson, FocusPatientVo.class);
		}
		return ReturnFomart.retParam(200, patient);
	}

	@Override
	public void exportExcel(String patientName, HttpServletRequest request, HttpServletResponse response) {
		List<FocusPatientExcel> list = focusPatientMapper.exportExcel(patientName);
		try {
			String fileName = new String("关注患者列表".getBytes(), "ISO-8859-1");
			ServletOutputStream out = response.getOutputStream();
			ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLS, true);

			Sheet sheet1 = new Sheet(1, 0, FocusPatientExcel.class);
			sheet1.setSheetName("第一个sheet");
			writer.write(list, sheet1);
			writer.finish();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-disposition", "attachment;filename="+fileName+".xls");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
