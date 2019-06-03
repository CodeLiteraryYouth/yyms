package com.leanin.repository;

import com.leanin.domain.dao.SatisfyFormStatDao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CPJ.
 * @date 2019/6/3.
 * @time 16:52.
 */
public interface SatisfyFormStatRepository extends JpaRepository<SatisfyFormStatDao,Long> {

    SatisfyFormStatDao findBySatisfyFormNumAndSatisfyPlanNum(String formNum,String planNum);
}
