package com.leanin.oauth.repository;

import com.leanin.domain.dao.UserWardDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWardRepostitory extends JpaRepository<UserWardDao,Long> {

    List<UserWardDao> findByUserId(Long userId);

    UserWardDao findByWardIdAndUserId(Long wardId,Long userId);

    void deleteByWardIdAndUserId(Long wardId,Long userId);

}
