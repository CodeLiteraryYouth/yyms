package com.leanin.testmodel;

import com.alibaba.fastjson.JSON;
import com.leanin.testmodel.config.RabbitMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
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

    @Autowired
    RabbitTemplate rabbitTemplate;

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
        Date date=new Date(1,2,30,6,0);
        System.out.println("原始数据："+date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        calendar.add(Calendar.DATE,1);//1年 2 月 3 星期 4 星期 5 日 6日 7
//        calendar.set(Calendar.HOUR_OF_DAY,21);
//        calendar.set(Calendar.DAY_OF_WEEK,2);
        calendar.set(Calendar.MONTH,0);
        calendar.set(Calendar.DAY_OF_MONTH,32);
        Date time = calendar.getTime();
        System.out.println("修改后的数据"+time);
    }


    @Test
    public void test111(){
        BoundHashOperations plan = redisTemplate.boundHashOps("plan");
    }

    @Test
    public void testfindById(){
        redisTemplate.delete("plan");
    }

    @Test
    public void testMQ(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,RabbitMQConfig.EX_ROUTING_PLAN_PATIENT,"发送数据");

    }

    @Test
    public void testJson(){
        Long[] longs=new Long[5];
        for (int i = 0; i < longs.length; i++) {
            longs[i]=1L+i;
        }


        System.out.println(JSON.toJSONString(longs));
    }


    @Test
    public void testdate(){
        Date date=new Date(1,2,30,6,0);
        long time = date.getTime();
        System.out.println(time);
        System.out.println("date:   "+date);
        Date date1 = new Date(1450786500000L);
        System.out.println("date1:  "+date1);
    }

    @Test
    public void sms(){
//        Long[] longs =new Long[5];
//        longs[0]=9L;
//        String string = JSON.toJSONString(longs);
//        System.out.println(string);
        System.out.println((1-2) > 0);
    }
}
