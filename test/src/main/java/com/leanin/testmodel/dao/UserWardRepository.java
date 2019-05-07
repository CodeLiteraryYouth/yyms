package com.leanin.testmodel.dao;

import com.leanin.domain.dao.UserWardDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWardRepository extends JpaRepository<UserWardDao,Long> {

}
