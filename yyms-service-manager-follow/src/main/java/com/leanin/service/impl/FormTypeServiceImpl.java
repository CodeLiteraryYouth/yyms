package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.*;
import com.leanin.mapper.*;
import com.leanin.service.FormTypeService;
import com.leanin.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FormTypeServiceImpl implements FormTypeService {

    @Autowired
    private FormTypeMapper formTypeMapper;

    @Autowired
    private FormInfoMapper formInfoMapper;

    @Autowired
    private SatisfyInfoMapper satisfyInfoMapper;

    @Autowired
    private MsgInfoMapper msgInfoMapper;

    @Autowired
    private RulesInfoMapper rulesInfoMapper;



    @Override
    public DataOutResponse findTypeList(Integer page, Integer pageSize, Integer typeStatus) {
        log.info("表单的类型为:" + typeStatus);
        PageHelper.startPage(page, pageSize);
        Page<FormTypeVo> pageList = (Page<FormTypeVo>) formTypeMapper.findTypeList(typeStatus);

        Map dataMap = new HashMap();
        dataMap.put("total", pageList.getTotal());
        dataMap.put("list", pageList.getResult());
        return ReturnFomart.retParam(200, dataMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse addFormType(FormTypeVo record) {
//		log.info("增加的表单类型数据为:"+ JSON.toJSONString(record));
//		FormTypeVo formType=formTypeMapper.findFormType(record.getFormTypeName());
//		if(CompareUtil.isNotEmpty(formType)) {
//			return ReturnFomart.retParam(4000, formType);
//		}
        formTypeMapper.addFormType(record);
        return ReturnFomart.retParam(200, record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updateTypeStatus(Long typeId) {
        FormTypeVo formTypeVo = formTypeMapper.findByFId(typeId);
        if (formTypeVo == null) {//信息不存在
            return ReturnFomart.retParam(1, "信息不存在");
        }
        switch (formTypeVo.getFormTypeInfo()) {
            case 1: {//随访表单
                List<FormInfoVo> list = formInfoMapper.findFormListByTypeId(typeId, null);
                if (list.size() != 0) {
                    return ReturnFomart.retParam(3901, "分类下有随访表单不能删除或者禁用");
                }
            }
            break;
            case 2: {//宣教表单
                List<FormInfoVo> list = formInfoMapper.findFormListByTypeId(typeId, null);
                if (list.size() != 0) {
                    return ReturnFomart.retParam(3902, "分类下有宣教表单不能删除或者禁用");
                }
            }
            break;
            case 3:{//随访规则
                List<RulesInfoVo> rulesList = rulesInfoMapper.findRulesList(null, null, typeId);
                if ( rulesList.size() > 0){
                    return ReturnFomart.retParam(3903,"分类下有随访规则不能删除或者禁用");
                }
            }
                break;
            case 4:{//宣教规则
                List<RulesInfoVo> rulesList = rulesInfoMapper.findRulesList(null, null, typeId);
                if ( rulesList.size() > 0){
                    return ReturnFomart.retParam(3904,"分类下有宣教规则不能删除或者禁用");
                }
            }
                break;
            case 5:{//满意度类型
                List<SatisfyInfoVo> satisfyList = satisfyInfoMapper.findSatisfyList(typeId, null, null);
                if (satisfyList.size() > 0){
                    return ReturnFomart.retParam(3905,"分类下有满意度表单不能删除或者禁用");
                }
            }
                break;
            case 6:{//短信提醒库类型
                List<MsgInfoVo> msgList = msgInfoMapper.findMsgListByTypeId(typeId, null);
                if (msgList.size() > 0){
                    return ReturnFomart.retParam(3906,"分类下有短信模板不能删除或者禁用");
                }
            }
                break;
            default:{//其他类型
                return ReturnFomart.retParam(2,"参数不存在或者错误");
            }
        }

        log.info("表单类型主键为:" + typeId);
        return ReturnFomart.retParam(200, formTypeMapper.updateTypeStatus(typeId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updateFormType(FormTypeVo record) {
        log.info("修改的表单类型数据为:" + JSON.toJSONString(record));
        formTypeMapper.updateFormType(record);
        return ReturnFomart.retParam(200, record);
    }

}
