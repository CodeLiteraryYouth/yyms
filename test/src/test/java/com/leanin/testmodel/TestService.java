package com.leanin.testmodel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestService {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test(){
//        redisTemplate.boundHashOps("test").put("test","test");
//        List o = (List) redisTemplate.boundHashOps("test").get("test");
//        Long delete = redisTemplate.boundHashOps("test").delete("test");
//        Object o = redisTemplate.boundHashOps("qq").get("q");

//        System.out.println("删除"+delete);
//        System.out.println(o);
        redisTemplate.boundHashOps("test01").put("q","qqq");
        String o = (String) redisTemplate.boundHashOps("test01").get("q");
        System.out.println("缓存中存的数据是o："+o);
        Long delete = redisTemplate.boundHashOps("test01").delete("q");
        String o1 = (String) redisTemplate.boundHashOps("test01").get("q");
        System.out.println("删除缓存中的key存的数据是o1："+o1+"返回结果是："+delete);
    }

    @Test
    public void testnull(){
        Map map=new HashMap();
        map.put("qq",null);

        Integer qq = (Integer) map.get("qq");
        System.out.println(qq);
    }

    @Test
    public void testCalendar(){
        Date date=new Date(1,2,3,6,0);
        System.out.println("原始数据："+date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,1);//1年 2 月 3 星期 4 星期 5 日 6日 7
        calendar.set(Calendar.HOUR_OF_DAY,21);
        Date time = calendar.getTime();
        System.out.println("修改后的数据"+time);
    }
}
