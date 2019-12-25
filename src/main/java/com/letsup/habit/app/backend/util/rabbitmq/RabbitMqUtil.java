package com.letsup.habit.app.backend.util.rabbitmq;

import com.letsup.habit.app.backend.vo.MessageVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqUtil {
    @Value("${spring.rabbitmq.directexchange.name}")
    private String directExchangeName;
    @Value("${spring.rabbitmq.directbindingkey}")
    private String directBindingKey;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 向direct类型的转换器发送消息
     * @param message
     */
    public void directConvertAndSend(MessageVo message) {
        this.rabbitTemplate.convertAndSend(this.directExchangeName, this.directBindingKey, message);
    }
}