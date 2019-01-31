package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.utils.AliyunOSSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;

/**
 * OSS文件上传
 * @author Administrator
 */
@RestController
@Slf4j
@RequestMapping(value="upload")
public class UploadController {

    @PostMapping(value = "uploadBlog")
    public DataOutResponse uploadBlog(@RequestParam MultipartFile file, HttpServletRequest request) {
        log.info("文件开始上传");
        String uploadUrl=null;
        try {

            if (null != file) {
                String filename = file.getOriginalFilename();
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    //上传到OSS
                    uploadUrl = AliyunOSSUtil.upload(newFile);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ReturnFomart.retParam(200, request.getRemoteAddr()+request.getRequestURI()+uploadUrl);
    }
}
