package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.dao.SatisfyFormStatDao;
import com.leanin.domain.request.FormQuest;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.Option;
import com.leanin.repository.SatisfyFormStatRepository;
import com.leanin.service.SatisfyFormStatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author CPJ.
 * @date 2019/6/3.
 * @time 16:57.
 */
@Service
@Slf4j
public class SatisfyFormStatServiceImpl implements SatisfyFormStatService {

    @Autowired
    SatisfyFormStatRepository satisfyFormStatRepository;

    @Override
    public DataOutResponse add(FormQuest formQuest) {
        SatisfyFormStatDao dao = satisfyFormStatRepository.findBySatisfyFormNumAndSatisfyPlanNum(formQuest.getFormNum(), formQuest.getPlanNum());
        if (dao == null){//没有表单记录则添加 表单记录
            //传进来的表单内容
            List<Map> maps = JSON.parseArray(formQuest.getOptionContent(), Map.class);
            for (Map map : maps) {//遍历题号
                Integer type = (Integer) map.get("type");
                // 判断是否是  1,5 //单选  2,6//多选
                if (type == 1 || type == 2 || type == 5 || type == 6){
                    List<Integer> radios = (List<Integer>) map.get("radio");
                    List<Option> options = JSON.parseArray(map.get("options").toString(), Option.class);
//                    List<Option> options = (List<Option>) map.get("options");
                    for (Option option : options) {
                        for (Integer radio : radios) {
                            if (option.getId() == radio){
                                option.setQuantity(option.getQuantity()+1);//有选项 加1
                            }
                        }
                    }
                    map.put("options",options);
                }
            }
            SatisfyFormStatDao satisfyFormStatDao = new SatisfyFormStatDao();
            satisfyFormStatDao.setSatisfyFormContent(JSON.toJSONString(maps));//表单内容
            satisfyFormStatDao.setSatisfyFormNum(formQuest.getFormNum()); //表单号
            satisfyFormStatDao.setSatisfyPlanNum(formQuest.getPlanNum()); //计划号
            satisfyFormStatDao.setRequestCount(1L);//访问次数
            log.info("添加的满意度表单选项统计"+JSON.toJSONString(satisfyFormStatDao));
            satisfyFormStatRepository.save(satisfyFormStatDao);
        }else {
            log.info("修改的满意度表单选项统计-->修改前-->"+JSON.toJSONString(dao));
            List<Map> maps = JSON.parseArray(formQuest.getOptionContent(), Map.class);
            List<Map> satisfyFormContent = JSON.parseArray(dao.getSatisfyFormContent(), Map.class);
            for (int i = 0; i < maps.size(); i++) {//遍历题号
                Map map = maps.get(i);
                Integer type = (Integer) map.get("type");
                // 判断是否是  1,5 //单选  2,6//多选
                if (type == 1 || type == 2 || type == 5 || type == 6){
                    Map map1 = satisfyFormContent.get(i);
//                    List<Option> options = (List<Option>) map1.get("options");
                    List<Option> options = JSON.parseArray(map1.get("options").toString(), Option.class);
                    List<Integer> radios = (List<Integer>) map.get("radio");
                    for (Option option : options) {
                        for (Integer radio : radios) {
                            if (option.getId() == radio){
                                option.setQuantity(option.getQuantity()+1);//有选项 加1
                            }
                        }
                    }
                    map1.put("options",options);
                }
            }
            dao.setSatisfyFormContent(JSON.toJSONString(satisfyFormContent));//
            dao.setRequestCount(dao.getRequestCount()+1);
            log.info("修改的满意度表单选项统计-->修改后-->"+JSON.toJSONString(dao));
            satisfyFormStatRepository.save(dao);
        }
        return ReturnFomart.retParam(200,"操作成功");
    }

    @Override
    public DataOutResponse findByPlanNumAndformId(String planNum, String formId) {
        log.info("满意度表单选项分析查询的参数-->"+"满意度计划号："+planNum+"满意度表单号:"+formId);
        SatisfyFormStatDao dao = satisfyFormStatRepository.findBySatisfyFormNumAndSatisfyPlanNum(formId, planNum);
        if (dao == null){
            return ReturnFomart.retParam(2011,planNum+":"+formId);
        }
        return ReturnFomart.retParam(200,dao);
    }
}
