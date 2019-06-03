package com.leanin.repository;

import com.leanin.controller.FollowFormStatController;
import com.leanin.domain.dao.FollowFormStatDao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CPJ.
 * @date 2019/6/3.
 * @time 15:35.
 */
public interface FollowFormStatRepository extends JpaRepository<FollowFormStatDao,Long> {
    FollowFormStatDao findByFollowFormNumAndPlanNum(String followFormNum,String planNum);
}
