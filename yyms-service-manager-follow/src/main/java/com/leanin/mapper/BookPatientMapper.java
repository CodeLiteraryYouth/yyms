package com.leanin.mapper;

import com.leanin.domain.dao.BookPatientDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BookPatientMapper {

    /**
     * 查询预约列表信息
     * @param patientName
     * @return
     */
    List<Map> findBookPatientList(@Param("patientName") String patientName, @Param("deptId") String deptId,
    @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 查询是否已经预约
     * @param doctorName
     * @param bookDate
     * @param patientId
     * @param bookType
     * @return
     */
    Map findBookPatient(@Param("doctorName") String doctorName,@Param("bookDate") String bookDate,
                        @Param("patientId") String patientId,@Param("bookType") int bookType);

    /**
     * 查询病人预约就诊日期的记录条数
     * @param bookDate
     * @param patientId
     * @return
     */
    int findcountOrder(@Param("bookDate") String bookDate,@Param("patientId") String patientId);
    /**
     * 增加预约信息列表
     * @param bookPatientDao
     * @return
     */
    int addBookPatient(BookPatientDao bookPatientDao);

    /**
     * 取消预约
     * @param patientId
     * @param doctorName
     * @param bookDate
     * @return
     */
    int updateBookStatus(@Param("patientId") String patientId,@Param("doctorName") String doctorName,@Param("bookDate") String bookDate);
}
