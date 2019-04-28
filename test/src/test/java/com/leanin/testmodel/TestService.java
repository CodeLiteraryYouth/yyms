package com.leanin.testmodel;

import com.leanin.domain.dao.UserWardDao;
import com.leanin.testmodel.dao.UserWardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestService {

    @Autowired
    UserWardRepository userWardRepository;

    @Test
    public void test(){
        UserWardDao userWardDao =new UserWardDao();
        userWardDao.setId(null);
        userWardDao.setWardId(1l);
        userWardDao.setUserId(1l);
        userWardDao.setCreate(1l);
        userWardDao.setCreateTime(new Date());
//        Optional<UserWardDao> byId = userWardRepository.findById(1L);
//        UserWardDao userWardDao1 = byId.get();
        UserWardDao save = userWardRepository.save(userWardDao);
        System.out.println(save);
    }
}
