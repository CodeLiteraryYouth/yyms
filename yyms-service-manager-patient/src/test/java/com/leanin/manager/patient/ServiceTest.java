package com.leanin.manager.patient;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.manager.patient.ManagerPatientApplication;
import com.leanin.manager.patient.service.ManagerPatientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTest {

    @Autowired
    ManagerPatientService managerPatientService;

    @Test
    public void test01(){
        Date startDate=new Date(116,1,4,0,0,0);
//        JSON.toJSONString(startDate)
//        System.out.println(JSON.toJSONString(startDate));
        Map map=new HashMap();
        map.put("currentPage",1);
        map.put("pageSize",10);
        map.put("inOut",1);
        map.put("startDate",startDate);
//
        DataOutResponse listByParam = managerPatientService.findListByParam(map);
        System.out.println(listByParam);
    }
}
