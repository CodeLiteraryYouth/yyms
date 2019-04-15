package com.leanin.service.impl;

import com.leanin.domain.LeaninAudioUpDao;
import com.leanin.domain.LeaninCallLogInfoDao;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.AdminUserVo;
import com.leanin.domain.vo.PlanPatientVo;
import com.leanin.dto.AudioUpDto;
import com.leanin.enumEntity.YesOrNoEnum;
import com.leanin.exception.CustomException;
import com.leanin.mapper.*;
import com.leanin.model.response.ResultCode;
import com.leanin.service.AudioUploadService;
import com.leanin.vo.CallLoginfoVo;
import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.MultimediaInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @ClassName AudioUploadServiceImpl
 * @Description 上传录音文件
 * @Author 刘壮
 * @Date 2019/4/8 14:37
 * @ModifyDate 2019/4/8 14:37
 * @Version 1.0
 */
@Service
@Slf4j
public class AudioUploadServiceImpl implements AudioUploadService {
    @Autowired
    private LeaninAudioUpDaoMapper leaninAudioUpDaoMapper;
   

    @Override
    @Transactional
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
            File source = new File(fileName);
            //将wav格式转为MP3格式
           // String replace = fileName.replace("wav", "mp3");
            file.transferTo(source);
            String uuidName=   fileName.substring(fileName.lastIndexOf(".")-32,fileName.lastIndexOf("."));
            this.execute(source,filePath+uuidName+".mp3");
            //解析文件时长
            Encoder encoder = new Encoder();
            MultimediaInfo m = encoder.getInfo(source);
            int duration =(int) m.getDuration()/1000;
            //文件信息
            LeaninAudioUpDao leaninAudioUpDao = new LeaninAudioUpDao();
            leaninAudioUpDao.setDuration(duration);
            leaninAudioUpDao.setAudioSource(0);
            leaninAudioUpDao.setFormat("mp3");
            leaninAudioUpDao.setCallUuid(uuidName);
            leaninAudioUpDao.setPath(uuidName+".mp3");
            leaninAudioUpDao.setCreateTime(new Date());
            leaninAudioUpDaoMapper.insert(leaninAudioUpDao);
            
        }catch(Exception e) {
            throw new CustomException(e.getMessage());
        }

        return   ReturnFomart.retParam(200, "文件上传成功");

    }
    /**
     * 执行转化
     *
     * @param source
     *            输入文件
     * @param desFileName  目标文件名
     * @return  转换之后文件
     */
    public static File execute(File source, String desFileName)
            throws Exception {
        File target = new File(desFileName);
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(new Integer(32000));
        audio.setChannels(new Integer(2));
        audio.setSamplingRate(new Integer(8000));
        audio.setVolume(768);
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        encoder.encode(source, target, attrs);
        return target;
    }

   
   
}
