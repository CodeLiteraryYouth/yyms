package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.client.ManagerPatientClient;
import com.leanin.domain.dao.BookPatientDao;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.MessageRecord;
import com.leanin.mapper.BookPatientMapper;
import com.leanin.mapper.MsgRecordMapper;
import com.leanin.service.BookPatientService;
import com.leanin.utils.CSMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zd
 */
@Service
@Slf4j
public class BookPatientServiceImpl implements BookPatientService {

    @Autowired
    private BookPatientMapper bookPatientMapper;

    @Autowired
    private ManagerPatientClient managerClient;

    @Autowired
    private MsgRecordMapper msgRecordMapper;


    @Override
    public DataOutResponse findBookPatientList(int page,int pageSize,String patientName,
                                               String deptId, String startDate, String endDate) {
        PageHelper.startPage(page, pageSize);
        List<Map> result=bookPatientMapper.findBookPatientList(patientName,deptId,startDate,endDate);
        PageInfo pageList=new PageInfo<>(result);
        return ReturnFomart.retParam(200,pageList);
    }

    @Override
    @Transactional
    public DataOutResponse addOrderPatient(BookPatientDao bookPatientDao) {
        log.info("保存预约病人信息为:"+ JSON.toJSONString(bookPatientDao));
        //增加预约列表的传输信息
        Map orderMap=new HashMap();
        orderMap.put("patientId",bookPatientDao.getPatientId());
        Map regMap=new HashMap();
        regMap.put("doctorId",bookPatientDao.getDoctorId());
        regMap.put("regDate",bookPatientDao.getSeeDocDate());
        regMap.put("regType",bookPatientDao.getBookType());
        //查询HIS中增加预约列表的显示信息
        DataOutResponse orderData=managerClient.updateOrderList(orderMap);
        //查询预约医生列表中的返回信息
        DataOutResponse regData=managerClient.updateRegnum(regMap);
        log.info("HIS预约列表信息:"+JSON.toJSONString(orderData)+"医生预约HIS信息为:"+JSON.toJSONString(regData));
        //判断feign服务没出现服务异常和增加数据成功则存储进入记录表
        if(orderData!=null && regData!=null && orderData.getStatus()==200 && regData.getStatus()==200) {
            StringBuilder builder=new StringBuilder();
            builder.append("已帮您预约xxxxx医院/r/n");
            builder.append("时间:"+bookPatientDao.getSeeDocDate()+(bookPatientDao.getBookType()==1?"上午":"下午"));
            builder.append("科室:"+bookPatientDao.getDeptName()+"/r/n");
            builder.append("医生:"+bookPatientDao.getDoctorName()+"/r/n");
            builder.append("请及时前往就诊");
            //预约成功以后发送短信给病人
            Map map=CSMSUtils.sendMessage(builder.toString(),bookPatientDao.getPhone());
            //将短信记录存储进入发送短信记录表
            MessageRecord msgRecord=new MessageRecord();
            msgRecord.setMsgSendName(bookPatientDao.getAdminId());
            msgRecord.setMsgSendDate(new Date());
            msgRecord.setMsgSendWard(bookPatientDao.getDeptName());
            msgRecord.setMsgText(builder.toString());
            msgRecord.setPatientId(bookPatientDao.getPatientId());
            msgRecord.setMsgSendNum(bookPatientDao.getPhone());
            msgRecord.setMsgSendStatus("true".equals(map.get("msg").toString()) ? 2:3);
            log.info("短信记录信息为:"+JSON.toJSONString(msgRecord));
            msgRecordMapper.addMsgRecord(msgRecord);
            bookPatientMapper.addBookPatient(bookPatientDao);
            return ReturnFomart.retParam(200,bookPatientDao);
        }
        return ReturnFomart.retParam(5001,bookPatientDao);
    }

    @Override
    @Transactional
    public DataOutResponse cancelOrderPatient(String patientId, String doctorName, String bookDate) {
        Map map=new HashMap();
        //
        //查询HIS中是否取消成功或者出现异常
        DataOutResponse cancelData=managerClient.cancelOrder(map);
        if(cancelData!=null && cancelData.getStatus()==200) {
            bookPatientMapper.updateBookStatus(patientId,doctorName,bookDate);
        }
        return ReturnFomart.retParam(200,patientId);
    }
}
