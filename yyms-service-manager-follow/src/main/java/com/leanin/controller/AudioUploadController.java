package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.service.AudioUploadService;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName AudioUploadController
 * @Description TODO
 * @Author 刘壮
 * @Date 2019/4/8 14:15
 * @ModifyDate 2019/4/8 14:15
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "uploadVoice",method = RequestMethod.POST)
public class AudioUploadController extends BaseController{
    @Autowired
    private AudioUploadService audioUploadService;
    public DataOutResponse uploadVoice(HttpServletRequest request,@RequestParam("file") MultipartFile file){
            try {
                return audioUploadService.uploadVoice(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return   ReturnFomart.retParam(200, "无文件上传");

    }
    }

