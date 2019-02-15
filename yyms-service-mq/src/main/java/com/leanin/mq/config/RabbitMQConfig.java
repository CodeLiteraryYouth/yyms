package com.leanin.mq.config;

import com.sun.javafx.logging.PulseLogger;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//rabbitMQ 配置类
@Configuration
public class RabbitMQConfig {

    //队列的名称
    public static final String QUEUE_INSERT_NAME = "plan_patient_queue";

    //满意度队列
    public static final String QUEUE_INSERT_SP="satisfyPlan_queue";

    //发送消息队列
    public static final String QUEUE_SEND_NAME="send_msg_queue";

    //交换机名称
    public static final String EXCHANGE_NAME ="plan_exchange";

    //满意度交换机
    public static final String EXCHANGE_NAME_SP="satisfyPlan_exchange";

    //发送消息的交换机
    public static final String Exchange_NAME_SEND="send_exchange";

    //路由名称
    public static final String EX_ROUTING_PLAN_PATIENT="ex_routing_plan_patient";

    //满意度路由名称
    public static final String EX_ROUTING_SATISFY_PLAN="ex_routing_satisfyPlan";

    //发送消息的路由
    public static final String EX_ROUTING_SEND="ex_routing_send";

    /**
     * 实例化交换机
     * @return
     */
    @Bean(EXCHANGE_NAME)
    public Exchange EX_ROUTING_PLAN_PATIENT(){
        Exchange exchange = ExchangeBuilder.directExchange(EXCHANGE_NAME).durable(true).build();
        return exchange;
    }
    //满意度交换机
    @Bean(EXCHANGE_NAME_SP)
    public Exchange EXCHANGE_NAME_SP(){
        Exchange exchange = ExchangeBuilder.directExchange(EXCHANGE_NAME_SP).durable(true).build();
        return exchange;
    }

    /**
     * 发送消息交换机
     * @return
     */
    @Bean(Exchange_NAME_SEND)
    public Exchange Exchange_NAME_SEND() {
        Exchange exchange = ExchangeBuilder.directExchange(Exchange_NAME_SEND).durable(true).build();
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
    //满意度队列
    @Bean(QUEUE_INSERT_SP)
    public Queue QUEUE_INSERT_SP(){
        Queue queue= new Queue(QUEUE_INSERT_SP);
        return queue;
    }

    /**
     * 发送消息的队列
     * @return
     */
    @Bean(QUEUE_SEND_NAME)
    public Queue QUEUE_SEND_NAME(){
        Queue queue= new Queue(QUEUE_SEND_NAME);
        return queue;
    }

    //将队列绑定到交换机上
    @Bean
    public Binding BINDING_PLAN_EXCHANGE(@Qualifier(EXCHANGE_NAME)Exchange exchange, @Qualifier(QUEUE_INSERT_NAME)Queue queue){
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(EX_ROUTING_PLAN_PATIENT).noargs();
        return binding;
    }

    //满意度队列绑定到 交换机上
    @Bean
    public Binding BINDING_STY_PLAN_EXCHANGE(@Qualifier(EXCHANGE_NAME_SP)Exchange exchange, @Qualifier(QUEUE_INSERT_SP)Queue queue){
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(EX_ROUTING_SATISFY_PLAN).noargs();
        return binding;
    }

    //发送消息的队列绑定到交换机上
    @Bean
    public Binding BINDING_SEND_EXCHANGE(@Qualifier(Exchange_NAME_SEND)Exchange exchange, @Qualifier(QUEUE_SEND_NAME)Queue queue){
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(EX_ROUTING_SEND).noargs();
        return binding;
    }
}
