package com.wonderful.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
* 描述:
* rabbitmq配置
*
* @author zj(admin)
* @create 2020-08-31 16:30
*/
@Slf4j
@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        log.info("============================>开始实例化实现rabbitmq的bean");
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        // 发布确认
        connectionFactory.setPublisherConfirms(true);
        // 消息发送失败返回到队列中
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new ContentTypeDelegatingMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        // 消息返回, yml需要配置 publisher-returns: true
        template.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.info("rabbitmq确认消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        });
        // 消息确认, yml需要配置 publisher-confirms: true
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                // log.debug("消息发送到exchange成功,id: {}", correlationData.getId());
                log.info("消息发送到exchange成功");
            } else {
                log.info("消息发送到exchange失败,原因: {}", cause);
            }
        });
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public Queue matchingQ() {
        return new Queue("wonderful.matching.q");
    }





    //死信队列（消费此队列）
    @Bean
    public Queue deadListenerUpdateStatusQueue() {
        return new Queue("dead.letter.clear.cache.q");
    }

    //死信队列交换机
    @Bean
    public DirectExchange deadListenerUpdateStatusExchange() {
        return new DirectExchange("dead.letter.clear.cache.e", true, false);
    }

    //死信交换机与死信队列绑定
    @Bean
    public Binding deadListenerUpdateStatusBinding() {
        return BindingBuilder.bind(deadListenerUpdateStatusQueue()).to(deadListenerUpdateStatusExchange()).with("dead_letter_clear_cache_routing_key");
    }

    //延时队列（推入此队列）
    @Bean
    public Queue delayListenerUpdateStatusQueue() {
        Map<String, Object> params = new HashMap<>();
        //x-dead-letter-exchange声明了队列里的死信转发到的DLX名称
        params.put("x-dead-letter-exchange", "dead.letter.clear.cache.e");
        //x-dead-letter-routing-key声明了这些死信在转发时携带的 routing-key名称
        params.put("x-dead-letter-routing-key", "dead_letter_clear_cache_routing_key");
        return new Queue("delay.clear.cache.q", true, false, false, params);
    }

    //延时队列交换机
    @Bean
    public DirectExchange delayListenerUpdateStatusExchange() {
        return new DirectExchange("delay.clear.cache.e", true, false);
    }

    //延时队列交换机与延时队列绑定
    @Bean
    public Binding delayListenerUpdateStatusBinding() {
        return BindingBuilder.bind(delayListenerUpdateStatusQueue()).to(delayListenerUpdateStatusExchange()).with("delay_clear_cache_routing_key");
    }

}
