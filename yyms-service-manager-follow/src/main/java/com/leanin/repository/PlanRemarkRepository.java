package com.leanin.repository;

import com.leanin.domain.dao.RemarkDao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CPJ.
 * @date 2019/6/5.
 * @time 17:09.
 */
public interface PlanRemarkRepository extends JpaRepository<RemarkDao,Long> {
}
