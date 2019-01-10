package com.leanin.testmodel.controller;

import com.leanin.api.test.TestApi;
import com.leanin.domain.vo.DiseaseInfoVo;
import com.leanin.testmodel.service.DiseaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController implements TestApi {

    @Autowired
    DiseaseInfoService diseaseInfoService;

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
