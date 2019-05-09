package com.leanin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.LeaninAudioUpDao;
import com.leanin.domain.LeaninCallLogInfoDao;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.AdminUserVo;
import com.leanin.domain.vo.PlanInfoVo;
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.dto.CallLogInfoDto;
import com.leanin.dto.query.QueryCallLogInfoDto;
import com.leanin.enumEntity.YesOrNoEnum;
import com.leanin.exception.CustomException;
import com.leanin.mapper.LeaninAudioUpDaoMapper;
import com.leanin.mapper.LeaninCallLogInfoDaoMapper;
import com.leanin.mapper.PlanInfoMapper;
import com.leanin.mapper.PlanPatientMapper;
import com.leanin.mapper.UserMapper;
import com.leanin.service.CallLogInfoService;
import com.leanin.vo.CallLoginfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CallLogInfoServiceImpl
 * @Description TODO
 * @Author 刘壮
 * @Date 2019/4/10 10:42
 * @ModifyDate 2019/4/10 10:42
 * @Version 1.0
 */
@Service
@Slf4j
public class CallLogInfoServiceImpl implements CallLogInfoService {
    @Autowired
    public LeaninCallLogInfoDaoMapper leaninCallLogInfoDaoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PlanPatientMapper planPatientMapper;
    @Autowired
    private PlanInfoMapper planInfoMapper;
    @Autowired
    private LeaninAudioUpDaoMapper leaninAudioUpDaoMapper;

    @Override
    public DataOutResponse findList(QueryCallLogInfoDto queryCallLogInfoDto) {
          Map dataMap =new HashMap();
          try{
              //进行分页查询
              PageHelper.startPage(queryCallLogInfoDto.getCurrentPage(),queryCallLogInfoDto.getPageSize());
              List<CallLoginfoVo> resultList =  leaninCallLogInfoDaoMapper.findList(queryCallLogInfoDto);
              dataMap.put("totalCount",((Page)resultList).getTotal());
              dataMap.put("list",resultList);
          }catch (Exception e){
                throw new CustomException(e.getMessage());
          }
        return ReturnFomart.retParam(200,dataMap);
    }

    @Override
    public DataOutResponse detail(Long callLogInfoId) {
        if(callLogInfoId == null){
            return ReturnFomart.retParam(404,"参数为空");
        }
      CallLogInfoDto dto = leaninCallLogInfoDaoMapper.findCallLogInfoById(callLogInfoId);
        if(null == dto){
            return ReturnFomart.retParam(404,"参数"+callLogInfoId+"，查出的对象不存在或者状态发生了变化");
        }
            return ReturnFomart.retParam(200,dto);
    }

    @Override
    @Transactional
    public DataOutResponse update(LeaninCallLogInfoDao leaninCallLogInfoDao) {
        if(null == leaninCallLogInfoDao){
            return ReturnFomart.retParam(404,"参数为空");
        }
        if(leaninCallLogInfoDao.getCallLogInfoId() == null){
            return ReturnFomart.retParam(404,"参数为空");
        }
        LeaninCallLogInfoDao entity = leaninCallLogInfoDaoMapper.selectByPrimaryKey(leaninCallLogInfoDao);
        if(null == entity){
            return ReturnFomart.retParam(404,"参数"+leaninCallLogInfoDao.getCallLogInfoId()+"，对象不存在或者状态发生变化");
        }
        if(YesOrNoEnum.YES.getCode().equals(entity.getIsDelete())){
            return ReturnFomart.retParam(404,"参数"+leaninCallLogInfoDao.getCallLogInfoId()+"，对象不存在或者状态发生变化");
        }
        entity.setRemark(leaninCallLogInfoDao.getRemark());
        leaninCallLogInfoDaoMapper.updateByPrimaryKey(entity);
        return ReturnFomart.retParam(200,entity);
    }

    @Override
    @Transactional
    public DataOutResponse delete(String ids) {
       if(StringUtils.isEmpty(ids)){
           return ReturnFomart.retParam(404,"参数为空");
       }
        String[] split = ids.split(",");

       for(String id : split){
           LeaninCallLogInfoDao entity = leaninCallLogInfoDaoMapper.selectByPrimaryKey(Long.parseLong(id));
           if(null == entity){
               return ReturnFomart.retParam(404,"参数"+id+"，对象不存在或者状态发生变化");
           }
           if(YesOrNoEnum.YES.getCode().equals(entity.getIsDelete())){
               return ReturnFomart.retParam(404,"参数"+id+"，对象不存在或者状态发生变化");
           }
           entity.setIsDelete(YesOrNoEnum.YES.getCode());
           leaninCallLogInfoDaoMapper.updateByPrimaryKey(entity);
       }
        return  ReturnFomart.retParam(200,"删除成功");
    }

	@Override
	public DataOutResponse insertCallLog(CallLogInfoDto callLogInfoDto) {
		  //创建通话记录实体类
        LeaninCallLogInfoDao leaninCallLogInfoDao = new LeaninCallLogInfoDao();
		try{
		//判断参数是否为空
          if(null == callLogInfoDto){
              return  ReturnFomart.retParam(404,"参数为空！");
          }
          if(StringUtils.isEmpty(callLogInfoDto.getFollowPlanId())){
              return  ReturnFomart.retParam(404,"随访id为空");
          }
          if(null == callLogInfoDto.getCallCreaterId()){
              return ReturnFomart.retParam(404,"呼叫人员id为空");
          }
          if(null == callLogInfoDto.getCustomerId()){
              return ReturnFomart.retParam(404,"被呼叫人id为空");
          }
         if(null == callLogInfoDto.getCallStartTime()){
              return ReturnFomart.retParam(404,"电话开始时间为空");
          }
          if(null == callLogInfoDto.getCallEndTime()){
              return ReturnFomart.retParam(404,"电话结束时间为空");
          }
          if(StringUtils.isEmpty(callLogInfoDto.getCallUuid())){
        	  return ReturnFomart.retParam(404,"电话号码唯一标识为空");
          }
          //查询电话记录是否存在
          LeaninAudioUpDao audioUpDao =  leaninAudioUpDaoMapper.selectByCallUuid(callLogInfoDto.getCallUuid());
          if(null == audioUpDao){
        	  log.error("电话唯一编码:"+callLogInfoDto.getCallUuid()+",电话录音不存在或者状态已经发生改变");
              throw new CustomException("电话唯一编码:"+callLogInfoDto.getCallUuid()+",电话录音不存在或者状态已经发生改变");
          }
          //获取时间差
          String timeString = getTimeString(callLogInfoDto.getCallStartTime(), callLogInfoDto.getCallEndTime());
        
         //查询随访计划
         PlanInfoVo planInfoById = planInfoMapper.findPlanInfoById(callLogInfoDto.getFollowPlanId());
         if(null == planInfoById){
        	 log.error("随访计划id:"+callLogInfoDto.getFollowPlanId()+",随访计划不存在或者状态已经发生改变");
             throw new CustomException("随访计划id:"+callLogInfoDto.getFollowPlanId()+",随访计划不存在或者状态已经发生改变");
         }
         
          //查询呼叫人员信息
          AdminUserVo adminUserVo = userMapper.findUserId(callLogInfoDto.getCallCreaterId());
          if(null == adminUserVo){
              log.error("呼叫人员id:"+callLogInfoDto.getCallCreaterId()+",呼叫人员不存在或者状态已经发生改变");
              throw new CustomException("呼叫人员id:"+callLogInfoDto.getCallCreaterId()+",呼叫人员不存在或者状态已经发生改变");
          }
          //记录呼叫人员信息
          leaninCallLogInfoDao.setCallCreaterId(adminUserVo.getAdminId());
          leaninCallLogInfoDao.setCallCreaterName(adminUserVo.getAdminName());
          leaninCallLogInfoDao.setCallCreaterNumber(adminUserVo.getPhone());
          leaninCallLogInfoDao.setSectionId(adminUserVo.getWardCode());
          leaninCallLogInfoDao.setSectionName(adminUserVo.getWardCode());
          leaninCallLogInfoDao.setHoldingTime(timeString);
          leaninCallLogInfoDao.setCallUuid(callLogInfoDto.getCallUuid());
          leaninCallLogInfoDao.setCallEndTime(callLogInfoDto.getCallEndTime());
          leaninCallLogInfoDao.setCallStartTime(callLogInfoDto.getCallStartTime());
          leaninCallLogInfoDao.setFollowPlanName(planInfoById.getPlanName());
          leaninCallLogInfoDao.setCustomerId(callLogInfoDto.getCustomerId());
          leaninCallLogInfoDao.setCustomerName(callLogInfoDto.getCustomerName());
          leaninCallLogInfoDao.setCustomerNumber(callLogInfoDto.getCustomerNumber());
          leaninCallLogInfoDao.setFollowPlanId(callLogInfoDto.getFollowPlanId());
          //查询被呼叫人员信息
         /* PlanPatientVo planPatientVo = planPatientMapper.findPlanPatientById(Long.parseLong(callLogInfoDto.getCustomerId().toString()));
          if(null == planPatientVo){
              log.error("被呼叫人员id"+callLogInfoDto.getCustomerId()+",被呼叫人员不存在或者状态已经发生改变");
              throw new CustomException("被呼叫人员id"+callLogInfoDto.getCustomerId()+",被呼叫人员不存在或者状态已经发生改变");

          }*/
          //记录被呼叫人员信息
         /* leaninCallLogInfoDao.setCustomerId(planPatientVo.getPatientPlanId());
          leaninCallLogInfoDao.setCustomerName(planPatientVo.getPatientName());
          leaninCallLogInfoDao.setCustomerNumber(planPatientVo.getPatientPhone());*/
          //记录通话记录状态
          leaninCallLogInfoDao.setCallType(0);
          leaninCallLogInfoDao.setCreateTime(new Date());
          leaninCallLogInfoDao.setIsDelete(YesOrNoEnum.NO.getCode());
          //插入通话记录
          leaninCallLogInfoDaoMapper.insert(leaninCallLogInfoDao);
          }catch(Exception e){
              throw new CustomException(e.getMessage());

          }
		return ReturnFomart.retParam(200,leaninCallLogInfoDao);
	}
	 /**
     * 获取时间差
     * @param startTime
     * @param endTime
     * @return
     */
    public String getTimeString(Date startTime,Date endTime){
        StringBuffer time = new StringBuffer();
        long between = endTime.getTime() - startTime.getTime();
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒");
        if(day >0){
            time.append(day + "天");
        }
        if(hour >0){
            time.append( hour + "小时");
        }
        if(min >0){
            time.append(min+"分");
        }
        if(s >0){
            time.append(s+"秒");
        }

        if(null == time){
            time.append("0");
        }
        return time.toString();
    }
}