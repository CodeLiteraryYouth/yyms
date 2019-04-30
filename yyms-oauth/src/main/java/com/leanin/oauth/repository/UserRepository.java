package com.leanin.oauth.repository;

import com.leanin.domain.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDao,Long> {

    UserDao findByWorkNumAndAdminStateIsNotIn(String workNum,Integer... status);

    UserDao findByAdminIdAndAdminStateNotIn(Long userId,Integer... status);
}
