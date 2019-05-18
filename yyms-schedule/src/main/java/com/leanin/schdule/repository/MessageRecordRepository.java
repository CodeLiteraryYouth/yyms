package com.leanin.schdule.repository;

import com.leanin.domain.vo.MessageRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CPJ.
 * @date 2019/5/18.
 * @time 21:27.
 */
public interface MessageRecordRepository extends JpaRepository<MessageRecord,Long> {
}
