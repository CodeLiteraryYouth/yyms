package com.leanin.controller;

import com.leanin.domain.dao.BookPatientDao;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.BookPatientService;
import com.leanin.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zd
 * @description 预约管理信息
 */
@RestController
@RequestMapping(value="book")
public class BookPatientController extends BaseController {

    @Autowired
    private BookPatientService bookService;


    /**
     * 查询预约患者
     * @param page
     * @param pageSize
     * @param patientName
     * @param deptId
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("findBookList")
    public DataOutResponse findBookList(@RequestParam int page,@RequestParam int pageSize,@RequestParam(required = false) String patientName,@RequestParam(required = false) String patientId,
                                        @RequestParam(required = false) String deptId,@RequestParam(required = false) String startDate,@RequestParam(required =false) String endDate) {
        return bookService.findBookPatientList(page,pageSize,patientName,patientId,deptId,startDate,endDate);
    }

    /**
     * 添加预约患者
     * @param bookDao
     * @return
     */
    @PostMapping("addBookPatient")
    public DataOutResponse addBookPatient(@RequestBody BookPatientDao bookDao) {
        return bookService.addOrderPatient(bookDao);
    }

    /**
     * 取消预约
     * @param patientId
     * @param doctorName
     * @param bookDate
     * @return
     */
    @GetMapping("cancelBook")
    public DataOutResponse cancelBook(@RequestParam String patientId,@RequestParam String doctorName,@RequestParam String bookDate) {
        return bookService.cancelOrderPatient(patientId,doctorName,bookDate);
    }

    /**
     * 预约患者导出excel
     * @param patientName
     * @param deptId
     * @param startDate
     * @param endDate
     */
    @GetMapping("exportBookExcel")
    public void exportBookExcel(String patientName, String deptId,String startDate,String endDate) {
        bookService.exportBookExcel(patientName,deptId,startDate,endDate,request,response);
    }

    /**
     * 就诊患者导出excel
     * @param patientName
     * @param deptId
     * @param startDate
     * @param endDate
     */
    @GetMapping("exportSeeDocExcel")
    public void exportSeeDocExcel(String patientName, String deptId,String startDate,String endDate) {
        bookService.exportSeeDocExcel(patientName,deptId,startDate,endDate,request,response);
    }
}
