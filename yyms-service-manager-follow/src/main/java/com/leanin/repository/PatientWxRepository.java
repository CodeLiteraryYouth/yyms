package com.leanin.repository;

import com.leanin.domain.dao.PatientWxDao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CPJ.
 * @date 2019/5/11.
 * @time 14:37.
 */
public interface PatientWxRepository extends JpaRepository<PatientWxDao,Long> {

    PatientWxDao findByOpenId(String openId);

    PatientWxDao findByIdCard(String idCard);
}
