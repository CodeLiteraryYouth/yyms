package com.leanin.testmodel.controller;

import com.leanin.api.test.TestApi;
import com.leanin.domain.vo.DiseaseInfoVo;
import com.leanin.testmodel.service.DiseaseInfoService;
import com.leanin.testmodel.task.MyScheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController implements TestApi {

    @Autowired
    DiseaseInfoService diseaseInfoService;

    @RequestMapping("/modify")
    public @ResponseBody String modify() throws SchedulerException {
        MyScheduler.modifyJob1("0/2 * * * * ?");
        return "1";
    }

    @RequestMapping("/status")
    public @ResponseBody
    String status() throws SchedulerException {
        return MyScheduler.getJob1Status();
    }

    @RequestMapping("/pause")
    public @ResponseBody String pause() throws SchedulerException {
        MyScheduler.pauseJob1();
        return "1";
    }

    @RequestMapping("/resume")
    public @ResponseBody String resume() throws SchedulerException {
        MyScheduler.resumeJob1();
        return "1";
    }

    @Override
    @GetMapping("/test/{param1}/{param2}")
    public String test(@PathVariable("param1") Integer param1, @PathVariable("param2") Integer param2) {
        return "这是一个测试的controller+参数1:"+param1+"参数2"+param2;
    }

    @Override
    @GetMapping("test02")
    public String test2(int param1, int param2) {
        return "测试方法2的两个参数："+param1+"参数2"+param2;
    }

    @Override
    @GetMapping("/findByDiseaseNum")
    public DiseaseInfoVo findByDiseaseNum(String diseaseNum) {
        return diseaseInfoService.findByDiseaseNum(diseaseNum);
    }

}
