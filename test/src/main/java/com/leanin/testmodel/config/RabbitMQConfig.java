package com.leanin.testmodel.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//rabbitMQ 配置类
@Configuration
public class RabbitMQConfig {

    //队列的名称
    public static final String QUEUE_INSERT_NAME = "plan_patient_queue";

    //交换机名称
    public static final String EXCHANGE_NAME ="plan_exchange";

    //交换机的名称
    public static final String EX_ROUTING_PLAN_PATIENT="ex_routing_plan_patient";

    /**
     * 实例化交换机
     * @return
     */
    @Bean(EXCHANGE_NAME)
    public Exchange EX_ROUTING_PLAN_PATIENT(){
        Exchange exchange = ExchangeBuilder.directExchange(EXCHANGE_NAME).durable(true).build();
        return exchange;
    }
    /**
     * 实例化队列对象
     * @return
     */
    @Bean(QUEUE_INSERT_NAME)
    public Queue QUEUE_INSERT_NAME(){
        Queue queue= new Queue(QUEUE_INSERT_NAME);
        return queue;
    }

    //将队列绑定到交换机上
    @Bean
    public Binding BINDING_PLAN_EXCHANGE(@Qualifier(EXCHANGE_NAME)Exchange exchange, @Qualifier(QUEUE_INSERT_NAME)Queue queue){
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(EX_ROUTING_PLAN_PATIENT).noargs();
        return binding;
    }
}
