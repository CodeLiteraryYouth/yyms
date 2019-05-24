package com.leanin.service.impl;

import java.util.Date;
import java.util.List;

import com.leanin.utils.LyOauth2Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leanin.domain.LeaninPatientRelaDao;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.enumEntity.YesOrNoEnum;
import com.leanin.mapper.LeaninPatientRelaDaoMapper;
import com.leanin.service.LeaninPatientRelaService;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author zliu
 *
 */
@Service
@Slf4j
public class LeaninPatientRelaServiceImpl implements LeaninPatientRelaService{
	@Autowired
	private LeaninPatientRelaDaoMapper leaninPatientRelaDaoMapper;
	
	@Override
	public DataOutResponse listPage(String patientId) {
		if(StringUtils.isEmpty(patientId)){
			return ReturnFomart.retParam(404, "参数为空！");
		}
		List<LeaninPatientRelaDao> list = leaninPatientRelaDaoMapper.listPage(patientId);
		return ReturnFomart.retParam(200,list);
	}

	@Override
	public DataOutResponse add(LeaninPatientRelaDao leaninPatientRelaDao, HttpServletRequest request) {
		if(null == leaninPatientRelaDao){
			return ReturnFomart.retParam(404, "参数为空！");
		}
		if(StringUtils.isEmpty(leaninPatientRelaDao.getPatientId())){
			return ReturnFomart.retParam(404, "患者id为空！");
		}
		//根据患者id  患者联系人 和 患者手机号查询 患者联系人
		LeaninPatientRelaDao patientRelaDao = leaninPatientRelaDaoMapper.findByNameAndReL(leaninPatientRelaDao.getPatientId(),leaninPatientRelaDao.getPatientRelaName(),leaninPatientRelaDao.getPatientRela(),leaninPatientRelaDao.getPatientRelaPhone());
		if (patientRelaDao != null){//相同的关系和姓名的 联系人不能重复添加
			return ReturnFomart.retParam(5200,leaninPatientRelaDao);
		}

		//添加患者联系人
		LyOauth2Util.UserJwt user = getUser(request);//获取当前登录的用户
		leaninPatientRelaDao.setCreateTime(new Date());//创建时间
		leaninPatientRelaDao.setIsDelete(YesOrNoEnum.NO.getCode());//设置状态 Y 删除  N 未删除
		leaninPatientRelaDao.setCreatorId(user.getId());//创建者id
		leaninPatientRelaDaoMapper.insertSelective(leaninPatientRelaDao);
		return ReturnFomart.retParam(200,leaninPatientRelaDao);
	}

	@Override
	public DataOutResponse findById(Long id) {
		if(null == id){
			return ReturnFomart.retParam(404, "参数为空！");
		}
		LeaninPatientRelaDao selectByPrimaryKey = leaninPatientRelaDaoMapper.selectByPrimaryKey(id);
		if(null == selectByPrimaryKey){
			log.error("id:"+id+"查出的的对象不存在");
			return ReturnFomart.retParam(404,"对象不存在");
		}
		if(!YesOrNoEnum.NO.getCode().equals(selectByPrimaryKey.getIsDelete())){
			log.error("id:"+id+"查出的的对象状态已经发生改变");
			return ReturnFomart.retParam(404,"查出的的对象状态已经发生改变");
		}
		
		return ReturnFomart.retParam(200,selectByPrimaryKey);
	}

	@Override
	public DataOutResponse update(LeaninPatientRelaDao leaninPatientRelaDao) {
		if(null == leaninPatientRelaDao){
			return ReturnFomart.retParam(404, "参数为空！");
		}
		if(leaninPatientRelaDao.getPatientRelaId()==null){
			return ReturnFomart.retParam(404, "参数为空！");
		}
		LeaninPatientRelaDao selectByPrimaryKey = leaninPatientRelaDaoMapper.selectByPrimaryKey(leaninPatientRelaDao.getPatientRelaId());
		if(null == selectByPrimaryKey){
			log.error("id:"+leaninPatientRelaDao.getPatientRelaId()+"查出的的对象不存在");
			return ReturnFomart.retParam(404,"对象不存在");
		}
		if(!YesOrNoEnum.NO.getCode().equals(selectByPrimaryKey.getIsDelete())){
			log.error("id:"+leaninPatientRelaDao.getPatientRelaId()+"查出的的对象状态已经发生改变");
			return ReturnFomart.retParam(404,"查出的的对象状态已经发生改变");
		}
		//设置与病人关系
		selectByPrimaryKey.setPatientRela(leaninPatientRelaDao.getPatientRela());
		//设置联系人姓名
		selectByPrimaryKey.setPatientRelaName(leaninPatientRelaDao.getPatientRelaName());
		//设置联系人电话
		selectByPrimaryKey.setPatientRelaPhone(leaninPatientRelaDao.getPatientRelaPhone());
		leaninPatientRelaDaoMapper.updateByPrimaryKey(selectByPrimaryKey);
		return ReturnFomart.retParam(200,selectByPrimaryKey);
	}

	@Override
	public DataOutResponse deleteByIds(String ids) {
		if(StringUtils.isEmpty(ids)){
			return ReturnFomart.retParam(404,"参数为空");
		}
		String[] split = ids.split(",");
		for(String id : split){
			LeaninPatientRelaDao selectByPrimaryKey = leaninPatientRelaDaoMapper.selectByPrimaryKey(Long.parseLong(id));
			if(null == selectByPrimaryKey){
				log.error("id:"+id+"查出的的对象不存在");
				return ReturnFomart.retParam(404,"对象不存在");
			}
			if(!YesOrNoEnum.NO.getCode().equals(selectByPrimaryKey.getIsDelete())){
				log.error("id:"+id+"查出的的对象状态已经发生改变");
				return ReturnFomart.retParam(404,"查出的的对象状态已经发生改变");
			}
			selectByPrimaryKey.setIsDelete(YesOrNoEnum.YES.getCode());
			leaninPatientRelaDaoMapper.updateByPrimaryKey(selectByPrimaryKey);
		}
		return ReturnFomart.retParam(200,"删除成功！");
	}

	private LyOauth2Util.UserJwt getUser(HttpServletRequest httpServletRequest){
		LyOauth2Util lyOauth2Util = new LyOauth2Util();
		LyOauth2Util.UserJwt userJwt = lyOauth2Util.getUserJwtFromHeader(httpServletRequest);
		return userJwt;
	}

}
