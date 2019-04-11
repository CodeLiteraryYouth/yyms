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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PlanPatientMapper planPatientMapper;
    @Autowired
    private LeaninCallLogInfoDaoMapper leaninCallLogInfoDaoMapper;

    @Override
    @Transactional
    public DataOutResponse uploadVoice(MultipartFile file, AudioUpDto audioUpDto) {
        try {
            //判断参数是否为空
          /*  if(null == audioUpDto){
                return  ReturnFomart.retParam(404,"参数为空！");
            }
            if(null == audioUpDto.getFollowPlanId()){
                return  ReturnFomart.retParam(404,"随访id为空");
            }
            if(null == audioUpDto.getCallCreaterId()){
                return ReturnFomart.retParam(404,"呼叫人员id为空");
            }
            if(null == audioUpDto.getCustomerId()){
                return ReturnFomart.retParam(404,"被呼叫人id为空");
            }*/
           /* if(null == audioUpDto.getCallStartTime()){
                return ReturnFomart.retParam(404,"电话开始时间为空");
            }
            if(null == audioUpDto.getCallEndTime()){
                return ReturnFomart.retParam(404,"电话结束时间为空");
            }*/
           /* //获取时间差
            String timeString = getTimeString(audioUpDto.getCallStartTime(), audioUpDto.getCallEndTime());*/
            //创建通话记录实体类
          /*  LeaninCallLogInfoDao leaninCallLogInfoDao = new LeaninCallLogInfoDao();

            //查询呼叫人员信息
            AdminUserVo adminUserVo = userMapper.findUserId(audioUpDto.getCallCreaterId());
            if(null == adminUserVo){
                log.error("呼叫人员id:"+audioUpDto.getCallCreaterId()+",呼叫人员不存在或者状态已经发生改变");
                throw new CustomException("呼叫人员id:"+audioUpDto.getCallCreaterId()+",呼叫人员不存在或者状态已经发生改变");
            }
            //记录呼叫人员信息
            leaninCallLogInfoDao.setCallCreaterId(adminUserVo.getAdminId());
            leaninCallLogInfoDao.setCallCreaterName(adminUserVo.getAdminName());
            leaninCallLogInfoDao.setCallCreaterNumber(adminUserVo.getPhone());
            leaninCallLogInfoDao.setSectionId(adminUserVo.getWardCode());
            //查询被呼叫人员信息
            PlanPatientVo planPatientVo = planPatientMapper.findPlanPatientById(Long.parseLong(audioUpDto.getCustomerId().toString()));
            if(null == planPatientVo){
                log.error("被呼叫人员id"+audioUpDto.getCustomerId()+",被呼叫人员不存在或者状态已经发生改变");
                throw new CustomException("被呼叫人员id"+audioUpDto.getCustomerId()+",被呼叫人员不存在或者状态已经发生改变");

            }
            //记录被呼叫人员信息
            leaninCallLogInfoDao.setCustomerId(planPatientVo.getPatientPlanId());
            leaninCallLogInfoDao.setCustomerName(planPatientVo.getPatientName());
            leaninCallLogInfoDao.setCustomerNumber(planPatientVo.getPatientPhone());*/

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
            //将wav格式转为MP3格式
            String replace = fileName.replace("wav", "mp3");
            file.transferTo(source);
            this.execute(source,filePath+replace);
            //解析文件时长
            Encoder encoder = new Encoder();
            MultimediaInfo m = encoder.getInfo(source);
            int duration =(int) m.getDuration()/1000;
            //文件信息
            LeaninAudioUpDao leaninAudioUpDao = new LeaninAudioUpDao();
            leaninAudioUpDao.setCallEndTime(audioUpDto.getCallEndTime());
            leaninAudioUpDao.setCallStartTime(audioUpDto.getCallStartTime());
            leaninAudioUpDao.setDuration(duration);
            leaninAudioUpDao.setAudioSource(0);
            leaninAudioUpDao.setFormat("mp3");
            leaninAudioUpDao.setPath(replace);
            /*leaninAudioUpDao.setHoldingTime(timeString);*/
            leaninAudioUpDao.setCreateTime(new Date());
            leaninAudioUpDaoMapper.insert(leaninAudioUpDao);
          /*  //插入文件id
            leaninCallLogInfoDao.setAutioUpId(leaninAudioUpDao.getAudioUpId());

            //记录通话记录状态
            leaninCallLogInfoDao.setCallType(0);
            leaninCallLogInfoDao.setCreateTime(new Date());
            leaninCallLogInfoDao.setIsDelete(YesOrNoEnum.NO.getCode());
            //插入通话记录
            leaninCallLogInfoDaoMapper.insert(leaninCallLogInfoDao);*/
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

    /**
     * 获取时间差
     * @param startTime
     * @param endTime
     * @return
     */
    public String getTimeString(Date startTime,Date endTime){
        StringBuffer time = new StringBuffer();
        long between = endTime.getTime() - startTime.getTime();
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒");
        if(day >0){
            time.append(day + "天");
        }
        if(hour >0){
            time.append( hour + "小时");
        }
        if(min >0){
            time.append(min+"分");
        }
        if(s >0){
            time.append(s+"秒");
        }

        if(null == time){
            time.append("0");
        }
        return time.toString();
    }
}
