package com.leanin.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leanin.domain.LeaninMedicalHistoryDao;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.enumEntity.YesOrNoEnum;
import com.leanin.mapper.LeaninMedicalHistoryDaoMapper;
import com.leanin.service.MedicalHistoryService;

import lombok.extern.slf4j.Slf4j;

/**
 * 疾病信息实现类
 * @author zliu
 *
 */
@Service
@Slf4j
public class MedicalHistoryServiceImpl implements MedicalHistoryService{
	@Autowired
	private LeaninMedicalHistoryDaoMapper  leaninMedicalHistoryDaoMapper;
	
	@Override
	public DataOutResponse listPage(String patientId) {
		// TODO Auto-generated method stub
			if(null == patientId){
				return ReturnFomart.retParam(404, "参数为空！");
				}
		List<LeaninMedicalHistoryDao>	list = leaninMedicalHistoryDaoMapper.listByPatientId(patientId);
		return ReturnFomart.retParam(200, list);
		
	}
	@Override
	public DataOutResponse addByType(LeaninMedicalHistoryDao leaninMedicalHistoryDao) {
		if(null == leaninMedicalHistoryDao){
			return ReturnFomart.retParam(404, "参数为空！");
		}
		if(StringUtils.isEmpty(leaninMedicalHistoryDao.getPatientInfoId())){
			return ReturnFomart.retParam(404, "患者id为空！");
		}
		if(leaninMedicalHistoryDao.getMedicalType() == null){
			return ReturnFomart.retParam(404, "疾病类型为空！");
		}
		leaninMedicalHistoryDao.setCreateTime(new Date());
		leaninMedicalHistoryDao.setIsDelete(YesOrNoEnum.NO.getCode());
		leaninMedicalHistoryDaoMapper.insert(leaninMedicalHistoryDao);
		return ReturnFomart.retParam(200, leaninMedicalHistoryDao);
	}
	@Override
	public DataOutResponse selectByid(Long id) {
		if(null == id){
			return ReturnFomart.retParam(404, "id为空");
		}
		
		LeaninMedicalHistoryDao  selectByPrimaryKey = leaninMedicalHistoryDaoMapper.selectByPrimaryKey(id);
		
		if(null == selectByPrimaryKey){
			log.error(id+"查出的对象为空");
			return ReturnFomart.retParam(404, "对象为空");
		}
		if(!selectByPrimaryKey.getIsDelete().equals(YesOrNoEnum.NO.getCode())){
			log.error(id+"查出的对象状态已经发生改变");
			return ReturnFomart.retParam(404, "查出的对象状态已经发生改变");
		}
		
		return ReturnFomart.retParam(200, selectByPrimaryKey);

	}
	@Override
	public DataOutResponse updateEntity(LeaninMedicalHistoryDao leaninMedicalHistoryDao) {
		if(null == leaninMedicalHistoryDao){
			 return ReturnFomart.retParam(404, "对象为空");
		}
		if(leaninMedicalHistoryDao.getMedicalHistoryId() == null){
			 return ReturnFomart.retParam(404, "对象id为空");
		}
		LeaninMedicalHistoryDao selectByPrimaryKey = leaninMedicalHistoryDaoMapper.selectByPrimaryKey(leaninMedicalHistoryDao.getMedicalHistoryId());
		if(selectByPrimaryKey == null){
			log.error("id:"+leaninMedicalHistoryDao.getMedicalHistoryId()+"查出的对象为空");
			 return ReturnFomart.retParam(404, "对象状态已经发生改变");
		}
		if(!YesOrNoEnum.NO.getCode().equals(selectByPrimaryKey.getIsDelete())){
			log.error("id:"+leaninMedicalHistoryDao.getMedicalHistoryId()+"查出的对象状态已经改变");
			 return ReturnFomart.retParam(404, "对象状态已经发生改变");
		}
		//设置参数
		selectByPrimaryKey.setMedicalDescription(leaninMedicalHistoryDao.getMedicalDescription());
		leaninMedicalHistoryDaoMapper.updateByPrimaryKeySelective(selectByPrimaryKey);
		
		return ReturnFomart.retParam(200, selectByPrimaryKey);
	}
	@Override
	public DataOutResponse deleteByIds(String ids) {
		if(StringUtils.isEmpty(ids)){
			 return ReturnFomart.retParam(404, "参数为空");
		}
		String[] split = ids.split(",");
		for(String id : split){
			LeaninMedicalHistoryDao selectByPrimaryKey = leaninMedicalHistoryDaoMapper.selectByPrimaryKey(Long.parseLong(id));
			if(selectByPrimaryKey == null){
				log.error("id:"+id+"查出的对象为空");
				 return ReturnFomart.retParam(404, "对象状态已经发生改变");
			}
			if(!YesOrNoEnum.NO.getCode().equals(selectByPrimaryKey.getIsDelete())){
				log.error("id:"+id+"查出的对象状态已经改变");
				 return ReturnFomart.retParam(404, "对象状态已经发生改变");
			}
			selectByPrimaryKey.setIsDelete(YesOrNoEnum.YES.getCode());
			leaninMedicalHistoryDaoMapper.updateByPrimaryKeySelective(selectByPrimaryKey);
		}
		return ReturnFomart.retParam(200, "删除成功！");
	}

}
