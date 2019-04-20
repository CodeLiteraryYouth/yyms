package com.leanin.controller;

import com.leanin.domain.common.ImportPatientReq;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @PostMapping("/importPatient")
    public DataOutResponse importPatient(@RequestBody List<ImportPatientReq> data,
                                         @RequestParam("planNum")String planNum,
                                         @RequestParam("planType") Integer planType){
        return commonService.importPatient(data,planNum,planType);
    }
}
