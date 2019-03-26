package com.leanin.testmodel.controller;

import com.aliyun.oss.OSSClient;
import com.leanin.api.test.TestApi;
import com.leanin.domain.vo.DiseaseInfoVo;
import com.leanin.testmodel.service.DiseaseInfoService;
import com.leanin.testmodel.task.MyScheduler;
import com.leanin.web.BaseController;
import org.junit.Test;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@RestController
@RequestMapping("/test")
public class TestController extends BaseController implements TestApi  {

    @Autowired
    DiseaseInfoService diseaseInfoService;

    private String accessKey = "LTAI9hPxQGlo79NT";
    private String accessSecret = "y10wZmbmV0U1RzWjcpPpMQWGcM5dgq";
    private String endpoint = "oss-cn-hangzhou.aliyuncs.com";

    /*@RequestMapping("/modify")
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
    }*/
    @PostMapping("/test")
    public String test() {
        String requestURI = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();
        return "测试方法";
    }


    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
//        file.transferTo();
        if (file == null || file.getSize() < 0) {
            return "请选择文件后在进行上传";
        }
        try {
            //获取文件名
            String originalFilename = file.getOriginalFilename();
            int index = originalFilename.indexOf(".");
            //获取格式
            String ext = originalFilename.substring(index);

            InputStream inputStream = file.getInputStream();
            File temporary = new File(originalFilename);
            FileOutputStream fos = new FileOutputStream(temporary);

            int len = 0;
            byte[] buffer = new byte[8192];
            while ((len = inputStream.read(buffer, 0, 8192)) != -1) {
                fos.write(buffer, 0, len);
            }
            OSSClient ossClient = new OSSClient(endpoint, accessKey, accessSecret);

            // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。  //D:\ly\yyms\test\src\main\resources\application.yml  //application.yml
            ossClient.putObject("audio-upload", "img/audio1", new File(originalFilename));

            // 关闭OSSClient。

//            boolean delete = temporary.delete();

            ossClient.shutdown();
            fos.close();
            inputStream.close();
//            URI uri = temporary.toURI();
//            File del = new File(temporary.toURI());
//            boolean delete = del.delete();
            boolean delete = temporary.delete();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        return "文件上传成功";

    }

    @Test
    public void del() {
        File file = new File("D:\\ly\\yyms\\a.test");
        boolean delete = file.delete();
        System.out.println(delete);
    }

    @Test
    public void test1(){
        String string = "123456";
        int index = string.indexOf(",");
        System.out.println(index);
    }
}
