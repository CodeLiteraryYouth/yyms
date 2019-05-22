package com.leanin.schdule.feign;

import com.leanin.domain.analysis.DeptAnalysis;
import com.leanin.domain.response.DataOutResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "yyms-service-manager-patient",fallback = FeignClientFallback.class)//制定远程调用的服务名
public interface ManagerPatientClient {


    //给随访提供接口，出住院病人信息
    @PostMapping("/managerPatient/findOutHosPatientByParamToSF")
    public Map findOutHosPatientByParamToSF(@RequestBody Map paramMap);

    //给随访提供接口，门诊病人信息
    @PostMapping("/managerPatient/findInHosPatientByParamToSF")
    public Map findInHosPatientByParamToSF(@RequestBody Map paramMap);


    //给随访提供接口，根据病人id查询 出住院记录
    @GetMapping("/managerPatient/findInHosPatientById")
    public Map findInHosPatientById(@RequestParam(value = "patientId", required = true) String patientId);

    //给随访提供接口，根据病人id查询 出住院记录
    @PostMapping("/managerPatient/findInHosRecordById")
    public List<Map> findInHosRecordById(@RequestBody Map paraMap);

    //给随访提供接口，根据病人id查询 门诊记录
    @GetMapping("/managerPatient/findOutHosPatientById")
    public Map findOutHosPatientById(@RequestParam(value = "patientId", required = true) String patientId);

    //给随访提供接口，根据病人id查询 门诊记录
    @GetMapping("/managerPatient/findOutHosRecordById")
    public List<Map> findOutHosRecordById(@RequestParam(value = "patientId", required = true) String patientId);


    /*@GetMapping("/user/findUserName")
    public String findUserName(@RequestParam Long adminId);*/

    /**
     * 增加预约次数
     * @param paramMap
     * @return
     */
    @PostMapping("/managerPatient/updateRegnum")
    public DataOutResponse updateRegnum(@RequestBody Map paramMap);

    /**
     * 查询预约医生列表
     * @param paramMap
     * @return
     */
    @PostMapping("/managerPatient/findRegList")
    public DataOutResponse findRegList(@RequestBody Map paramMap);

    /**
     * 增加预约列表信息
     * @param paramMap
     * @return
     */
    @PostMapping("/managerPatient/updateOrderList")
    public DataOutResponse updateOrderList(@RequestBody Map paramMap);

    /**
     * 取消预约
     * @param paramMap
     * @return
     */
    @PostMapping("/managerPatient/cancelOrder")
    public DataOutResponse cancelOrder(@RequestBody Map paramMap);


    @GetMapping("/managerPatient/findByIdCard")
    public DataOutResponse findByIdCard(@RequestParam("idCard") String idCard, @RequestParam("patientName") String patientName);


    @GetMapping("/managerPatient/findInOutCount")
    public List<DeptAnalysis> findInOutCount(@RequestParam(name = "dept", required = false) String dept,
                                             @RequestParam(name = "startDate", required = false) String startDate,
                                             @RequestParam(name = "endDate", required = false) String endDate,
                                             @RequestParam(value = "inOut", required = true) Integer inOut);


    @GetMapping("/managerPatient/findInOutCountByYear")
    public List<DeptAnalysis> findInOutCountByYear(@RequestParam(name = "dept", required = false) String dept,
                                                   @RequestParam(name = "year", required = true) String year,
                                                   @RequestParam(name = "inOut", required = true) Integer inOut);
}
