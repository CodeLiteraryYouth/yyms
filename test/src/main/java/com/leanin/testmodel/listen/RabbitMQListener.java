package com.leanin.testmodel.listen;

import com.leanin.testmodel.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class RabbitMQListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_INSERT_NAME)
    public void Lis(String qqq){

        System.out.println("接收到的消息是："+qqq);
    }
}
