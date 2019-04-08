package com.leanin.service;

import com.leanin.domain.response.DataOutResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName AudioUploadService
 * @Description TODO
 * @Author 刘壮
 * @Date 2019/4/8 14:24
 * @ModifyDate 2019/4/8 14:24
 * @Version 1.0
 */
public interface AudioUploadService  {
        /**
         * 录音上传
         * @param file
         */
        DataOutResponse uploadVoice(MultipartFile file);
}
