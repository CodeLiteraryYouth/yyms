package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.dto.AudioUpDto;
import com.leanin.service.AudioUploadService;
import com.leanin.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName 上传录音文件
 * @Description TODO
 * @Author 刘壮
 * @Date 2019/4/8 14:15
 * @ModifyDate 2019/4/8 14:15
 * @Version 1.0
 */
@RestController
@RequestMapping("audioUpload")
public class AudioUploadController extends BaseController{
    @Autowired
    private AudioUploadService audioUploadService;

    @RequestMapping(value = "uploadVoice")
    public DataOutResponse uploadVoiceget(HttpServletRequest request
                                      ) {
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
                Optional<MultipartFile> optional = fileMap.values().stream().findFirst();
                if (!optional.isPresent()) {
                    return ReturnFomart.retParam(404, "无文件上传");
                }

                MultipartFile file = optional.get();

                return audioUploadService.uploadVoice(file);
            }else{
                return ReturnFomart.retParam(404, "无文件上传");
            }
        } catch(Exception e){
                return ReturnFomart.retParam(404, e.getMessage());
            }

    }
}
