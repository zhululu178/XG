package com.letsup.habit.app.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.rabbitmq.directexchange.name}")
    private String directExchangeName;
    @Value("${spring.rabbitmq.directbindingkey}")
    private String directBindingKey;
    @Value("${spring.rabbitmq.directqueue}")
    public String directQueue;

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        // 消息是否成功发送到Exchange
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            this.logger.info(correlationData.toString());
            if (ack) {
                this.logger.info("收到ack");
            }
        });
        // 触发setReturnCallback回调必须设置mandatory=true, 否则Exchange没有找到Queue就会丢弃掉消息, 而不会触发回调
        rabbitTemplate.setMandatory(true);

        // 消息是否从Exchange路由到Queue, 注意: 这是一个失败回调, 只有消息从Exchange路由到Queue失败才会回调这个方法
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            this.logger.info(String.format("===%s===", "returnCallBack"));
            this.logger.info("message = " + message);
            this.logger.info("replyCode = " + replyCode);
            this.logger.info("replyText = " + replyText);
            this.logger.info("exchange = " + exchange);
            this.logger.info("routingKey = " + routingKey);
        });
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new ContentTypeDelegatingMessageConverter(new Jackson2JsonMessageConverter());
    }

    //队列 起名：TestDirectQueue
    @Bean
    public Queue DirectQueue() {
        return new Queue(this.directQueue,true);  //true 是否持久
    }

    //Direct交换机 起名：TestDirectExchange
    @Bean
    public DirectExchange DirectExchange() {
        return new DirectExchange(directExchangeName);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(DirectQueue()).to(DirectExchange()).with(this.directBindingKey);
    }
}