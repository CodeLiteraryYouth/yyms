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
@RestController(value="book")
public class BookPatientController extends BaseController {

    @Autowired
    private BookPatientService bookService;


    @GetMapping("findBookList")
    public DataOutResponse findBookList(@RequestParam int page,@RequestParam int pageSize,@RequestParam(required = false) String patientName,
                                        @RequestParam(required = false) String deptId,@RequestParam(required = false) String startDate,@RequestParam(required =false) String endDate) {
        return bookService.findBookPatientList(page,pageSize,patientName,deptId,startDate,endDate);
    }

    @PostMapping("addBookPatient")
    public DataOutResponse addBookPatient(@RequestBody BookPatientDao bookDao) {
        return bookService.addOrderPatient(bookDao);
    }

    @GetMapping("cancelBook")
    public DataOutResponse cancelBook(@RequestParam String patientId,@RequestParam String doctorName,@RequestParam String bookDate) {
        return bookService.cancelOrderPatient(patientId,doctorName,bookDate);
    }
}
