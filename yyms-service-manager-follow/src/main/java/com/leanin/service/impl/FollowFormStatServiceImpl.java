package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.controller.FollowFormStatController;
import com.leanin.domain.dao.FollowFormStatDao;
import com.leanin.domain.request.FormQuest;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.FormInfoVo;
import com.leanin.domain.vo.Option;
import com.leanin.mapper.FormInfoMapper;
import com.leanin.repository.FollowFormStatRepository;
import com.leanin.service.FollowFormStatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author CPJ.
 * @date 2019/6/3.
 * @time 14:06.
 */
@Service
@Slf4j
public class FollowFormStatServiceImpl implements FollowFormStatService {


    @Autowired
    FollowFormStatRepository followFormStatRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse add(FormQuest formQuest) {
        FollowFormStatDao followFormStat = followFormStatRepository.findByFollowFormNumAndPlanNum(formQuest.getFormNum(), formQuest.getPlanNum());
        if (followFormStat == null){//没有表单记录则添加 表单记录
            //传进来的表单内容
            List<Map> maps = JSON.parseArray(formQuest.getOptionContent(), Map.class);
            for (Map map : maps) {//遍历题号
                Integer type = (Integer) map.get("type");
                // 判断是否是  1,5 //单选  2,6//多选
                if (type == 1 || type == 2 || type == 5 || type == 6){
                    List<Integer> radios = (List<Integer>) map.get("radio");
                    List<Option> options = JSON.parseArray(map.get("options").toString(), Option.class);
//                    List<Option> options = (List<Option>) ;
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
            FollowFormStatDao followFormStatDao = new FollowFormStatDao();
            followFormStatDao.setFollowFormContent(JSON.toJSONString(maps));//表单内容
            followFormStatDao.setFollowFormNum(formQuest.getFormNum()); //表单号
            followFormStatDao.setPlanNum(formQuest.getPlanNum()); //计划号
            followFormStatDao.setFormTitle(formQuest.getFormTitle()); //计划号
            followFormStatDao.setFormBottom(formQuest.getFormBottom()); //计划号
            followFormStatDao.setRequestCount(1L);//访问次数
            log.info("添加的随访表单选项统计"+JSON.toJSONString(followFormStatDao));
            FollowFormStatDao save = followFormStatRepository.save(followFormStatDao);
        }else {
            log.info("修改的随访表单选项统计-->修改前-->"+JSON.toJSONString(followFormStat));
            List<Map> maps = JSON.parseArray(formQuest.getOptionContent(), Map.class);
            List<Map> followFormContent = JSON.parseArray(followFormStat.getFollowFormContent(), Map.class);
            for (int i = 0; i < maps.size(); i++) {//遍历题号
                Map map = maps.get(i);
                Integer type = (Integer) map.get("type");
                // 判断是否是  1,5 //单选  2,6//多选
                if (type == 1 || type == 2 || type == 5 || type == 6){
                    Map map1 = followFormContent.get(i);
//                    List<Option> options = (List<Option>) map1.get("options");
                    List<Option> options = JSON.parseArray(map1.get("options").toString(), Option.class);
                    List<Integer> radios = (List<Integer>) map.get("radio");
                    if(radios.size() <= 0){//没有选择
                        continue;
                    }
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
            followFormStat.setFollowFormContent(JSON.toJSONString(followFormContent));//
            followFormStat.setRequestCount(followFormStat.getRequestCount()+1);
            log.info("修改的随访表单选项统计-->修改后-->"+JSON.toJSONString(followFormStat));
            followFormStatRepository.save(followFormStat);
        }
        return ReturnFomart.retParam(200,"操作成功");
    }

    @Override
    public DataOutResponse findByFormIdAndPlanId(String planNum, String formId) {
        log.info("查询随访表单选项统计传递的参数--->"+"计划号："+planNum+"表单号："+formId);
        FollowFormStatDao dao = followFormStatRepository.findByFollowFormNumAndPlanNum(formId, planNum);
        if (dao == null){
            return ReturnFomart.retParam(2011,planNum+":"+formId);
        }
        return ReturnFomart.retParam(200,dao);
    }
}
