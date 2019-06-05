package com.leanin.service;

        import com.leanin.domain.dao.BookPatientDao;
        import com.leanin.domain.response.DataOutResponse;

        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;

/**
 * @author zd
 * @Date 2019/5/9 14:24
 * @description 预约管理信息
 */
public interface BookPatientService {

    /**
     * 查询预约列表信息
     * @param patientName
     * @param deptId
     * @param startDate
     * @param endDate
     * @return
     */
    DataOutResponse findBookPatientList(int page,int pageSize,String patientName,String patientId,
                                        String deptId, String startDate,String endDate);

    /**
     * 增加预约列表信息
     * @param bookPatientDao
     * @return
     */
    DataOutResponse addOrderPatient(BookPatientDao bookPatientDao);

    /**
     * 取消预约
     * @param patientId
     * @param doctorName
     * @param bookDate
     * @return
     */
    DataOutResponse cancelOrderPatient(String patientId,String doctorName,String bookDate);

    void exportBookExcel(String patientName, String deptId, String startDate, String endDate, HttpServletRequest request, HttpServletResponse response);

    void exportSeeDocExcel(String patientName, String deptId, String startDate, String endDate, HttpServletRequest request, HttpServletResponse response);
}
