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
