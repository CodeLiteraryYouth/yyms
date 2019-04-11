package com.leanin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.LeaninCallLogInfoDao;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.dto.CallLogInfoDto;
import com.leanin.dto.query.QueryCallLogInfoDto;
import com.leanin.enumEntity.YesOrNoEnum;
import com.leanin.exception.CustomException;
import com.leanin.mapper.LeaninCallLogInfoDaoMapper;
import com.leanin.service.CallLogInfoService;
import com.leanin.vo.CallLoginfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        @Override
        public DataOutResponse findList(QueryCallLogInfoDto queryCallLogInfoDto) {
                          Map dataMap =new HashMap();
                          try{//进行分页查询
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
}