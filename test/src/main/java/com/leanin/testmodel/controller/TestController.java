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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
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

    @Test
    public void bc(){
        //$2a$10$bN2SGsAWI4v3tCgqQx2/F.VNBKSGchE.qhpml6nyNkgfJJhsYbyUa
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode("d8f8c8d302982d6d540d10628749db25");
        System.out.println(password);

    }

    @Test
    public void jwttoken(){
        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1wtzjn4om6eZNB9oxB69/GYbvPl0ncGWP8Semdyfu4YH0rsBj4jmB14BRoAKYT3IQfW8YyZY8okdHLaSA0GAgTq36u+JKYQ0D4q1V7ufEVkFeTFgvEUrxs+Utuuju+vWvkDO7fmHgqbEgy5AKs1eHTtev7LDNwbg6EKFwOl8rdYLww7enEkVYyP1Je5tlCubc7sXIA4JQnofhbI5wIQGUXpAzAeuQmDlfuT5QzLhRSzZBuAO5+dSwXtNa/x0NUVP/VDOAdfOo+5IZQalEo77CMHsR3eJp+v96Mz+8spHhsQUHAWs14p7q4ooIZ36PDt0FuNAosKCh+maWOXNpSZiDwIDAQAB-----END PUBLIC KEY-----";
        //jwt令牌
        String jwtString = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTUzNDE4NDEsInVzZXJfbmFtZSI6ImxlYW5pbjk1MjciLCJhdXRob3JpdGllcyI6WyJyb290Il0sImp0aSI6ImRhNDRlNmQ2LTI4OGYtNGYxZC04OTJkLTI2ZGZiOGZkMTkyNyIsImNsaWVudF9pZCI6IkxlYW5pbldlYkFwcCIsInNjb3BlIjpbImFwcCJdfQ.7B-ARIa422L1BqHB_bbsljyucaDAjkUwD3wu1C3-2TM";
        //校验jwt令牌
        Jwt jwt = JwtHelper.decodeAndVerify(jwtString, new RsaVerifier(publickey));
        //拿到jwt令牌中自定义的内容
        String claims = jwt.getClaims();
        System.out.println(claims);
    }
}
