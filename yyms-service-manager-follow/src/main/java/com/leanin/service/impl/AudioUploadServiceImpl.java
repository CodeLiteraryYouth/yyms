package com.leanin.service.impl;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.service.AudioUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

/**
 * @ClassName AudioUploadServiceImpl
 * @Description TODO
 * @Author 刘壮
 * @Date 2019/4/8 14:37
 * @ModifyDate 2019/4/8 14:37
 * @Version 1.0
 */
@Service
@Slf4j
public class AudioUploadServiceImpl implements AudioUploadService {

    @Override
    public DataOutResponse uploadVoice(MultipartFile file) {
        try {
            String filePath="";
        	String os = System.getProperty("os.name");
       	if (os.startsWith("Windows")) {
       		filePath="D:\\upload\\";
       	}else if (os.startsWith("Linux")) {
        		filePath="/home/tp/wav/";
       	}

            String fileName = file.getOriginalFilename();
            File fileSourcePath = new File(filePath);
            if (!fileSourcePath.exists()) {
                fileSourcePath.mkdirs();
            }
            File source = new File(filePath+fileName);
            file.transferTo(source);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return   ReturnFomart.retParam(200, "文件上传成功");

    }

}
