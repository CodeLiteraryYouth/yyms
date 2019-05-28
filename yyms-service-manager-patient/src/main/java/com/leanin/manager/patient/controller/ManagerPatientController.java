package com.leanin.manager.patient.controller;


import com.leanin.api.patient.ManagerPatientApi;
import com.leanin.domain.analysis.DeptAnalysis;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.manager.patient.service.ManagerPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/managerPatient")
public class ManagerPatientController implements ManagerPatientApi {

    @Autowired
    ManagerPatientService managerPatientService;

    /**
     * 分页查询出住院患者信息
     * patientName  患者名称
     * inhosNo 出住院号
     * inOut 1在院 2出院
     * dept 科室名称  模糊查询
     * dist 病区 模糊查询
     * startDate 开始时间
     * endDate 结束时间
     * sex 1 男 2 女
     * startAge 开始年龄
     * endAge  结束年龄
     * noContact 是否有联系方式  true 无联系方式  false 有联系方式
     * @param paramMap
     * @return
     */
    @Override
    @PostMapping("/findPatientList")
    public DataOutResponse findListByParam(@RequestBody Map paramMap) {
        return managerPatientService.findListByParam(paramMap);
    }

    /**
     * 分页查询就诊患者信息
     * @param paramMap
     * @return
     */
    @Override
    @PostMapping("/findOutHosPatientByParam")
    public DataOutResponse findOutHosPatientByParam(@RequestBody Map paramMap) {
/*        return managerPatientService.findOutHosPatientByParam(paramMap);
*/        return managerPatientService.findListByParam(paramMap);

    }
    //给随访提供接口，出住院病人信息
    @Override
    @PostMapping("/findOutHosPatientByParamToSF")
    public Map findOutHosPatientByParamToSF(@RequestBody Map paramMap) {
        return managerPatientService.findOutHosPatientByParamToSF(paramMap);
    }
    //给随访提供接口，门诊病人信息
    @Override
    @PostMapping("/findInHosPatientByParamToSF")
    public Map findInHosPatientByParamToSF(@RequestBody Map paramMap) {
        return managerPatientService.findInHosPatientByParamToSF(paramMap);
    }

    //给随访提供接口，根据病人id查询 出住院记录
    @Override
    @GetMapping("findInHosPatientById")
    public Map findInHosPatientById(@RequestParam(value = "patientId",required = true)String patientId) {

        return managerPatientService.findInHosPatientById(patientId);
    }

    //给随访提供接口，根据病人id查询 出住院记录
    @Override
    @PostMapping("findInHosRecordById")
    public List<Map> findInHosRecordById(@RequestBody Map paraMap) {
        return managerPatientService.findInHosRecordById(paraMap);
    }

    //给随访提供接口，根据病人id查询 门诊记录
    @Override
    @GetMapping("findOutHosPatientById")
    public Map findOutHosPatientById(@RequestParam(value = "patientId",required = true) String patientId) {
        return managerPatientService.findOutHosPatientById(patientId);
    }

    //给随访提供接口，根据病人id查询 门诊记录
    @Override
    @GetMapping("findOutHosRecordById")
    public List<Map> findOutHosRecordById(@RequestParam(value = "patientId",required = true) String patientId) {
        return managerPatientService.findOutHosRecordById(patientId);
    }

    @Override
    @PostMapping("findRegList")
    public DataOutResponse findRegList(@RequestBody Map parmMap) {
        return managerPatientService.findRegList(parmMap);
    }

    @Override
    @PostMapping("updateRegnum")
    public DataOutResponse updateRegnum(@RequestBody Map paramMap) {
        return managerPatientService.updateRegNum(paramMap);
    }

    @Override
    @PostMapping("updateOrderList")
    public DataOutResponse updateOrderList(Map paramMap) {
        return managerPatientService.updateOrderInfo(paramMap);
    }

    @Override
    @GetMapping("findDoctorDept")
    public DataOutResponse findDoctorDept() {
        return managerPatientService.findDoctorDept();
    }

    @Override
    @PostMapping("cancelOrder")
    public DataOutResponse cancelOrder(Map paramMap) {
        return managerPatientService.cancelOrder(paramMap);
    }

    @Override
    @GetMapping("findByIdCard")
    public DataOutResponse findByIdCard(@RequestParam("idCard") String idCard,@RequestParam("patientName")String patientName) {
        return managerPatientService.findByIdCard(idCard,patientName);
    }

    @Override
    @GetMapping("findInOutCount")
    public List<DeptAnalysis> findInOutCount(@RequestParam(name = "dept",required = false) String dept,
                                             @RequestParam(name = "startDate",required = false) String startDate,
                                             @RequestParam(name = "endDate",required = false) String endDate,
                                             @RequestParam(value = "inOut",required = true) Integer inOut){
        return managerPatientService.findInOutCount(dept,startDate,endDate,inOut);
    }


    @Override
    @GetMapping("findInOutCountByYear")
    public List<DeptAnalysis> findInOutCountByYear(@RequestParam(name = "dept",required = false) String dept,
                                                   @RequestParam(name = "year",required = true) String year,
                                                   @RequestParam(name = "inOut",required = true) Integer inOut) {
        return managerPatientService.findInOutCountByYear(dept,year,inOut);
    }




}