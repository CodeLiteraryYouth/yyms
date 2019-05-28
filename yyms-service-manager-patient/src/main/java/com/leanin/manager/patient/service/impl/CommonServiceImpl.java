package com.leanin.manager.patient.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.manager.patient.service.CommonService;
import com.leanin.webserviceclient.WebServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author CPJ.
 * @date 2019/5/28.
 * @time 20:09.
 */
@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public DataOutResponse getDept() {
        //先从redis缓存中查看有没有患者科室数据
        String deptString = stringRedisTemplate.opsForValue().get("dept");
        if (null != deptString){//有缓存用缓存
            log.info("调用患者科室存在缓存"+deptString);
            List<Map> maps = JSON.parseArray(deptString, Map.class);
            return ReturnFomart.retParam(200,maps);
        }else {//没有缓存调用his 获取患者科室并存入缓存
            Client client = WebServiceClient.createClient();
            try {
                Object[] objects = client.invoke("findDept");
                String result = objects[0].toString();
                log.info("调用患者科室没有缓存调用his获取患者科室"+result);
                stringRedisTemplate.opsForValue().set("dept",result,1 ,TimeUnit.DAYS);
                List<Map> maps = JSON.parseArray(result, Map.class);
                return ReturnFomart.retParam(200,maps);
            } catch (Exception e) {
                log.info("调用his患者科室失败");
                e.printStackTrace();
                return ReturnFomart.retParam(5300,"获取患者科室失败");
            }
        }

    }
}
