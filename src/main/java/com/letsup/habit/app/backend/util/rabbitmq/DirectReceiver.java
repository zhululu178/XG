package com.letsup.habit.app.backend.util.rabbitmq;

import com.letsup.habit.app.backend.config.RabbitConfig;
import com.letsup.habit.app.backend.constants.MessageType;
import com.letsup.habit.app.backend.dao.entity.HabAppHabitTemplate;
import com.letsup.habit.app.backend.dao.mapper.HabAppHabitTemplateMapperExt;
import com.letsup.habit.app.backend.vo.MessageVo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DirectReceiver {
    @Autowired
    private HabAppHabitTemplateMapperExt habAppHabitTemplateMapperExt;

    /**
     * 监听direct消息
     * @param message
     */
    @RabbitListener(queues = "${spring.rabbitmq.directqueue}")//监听的队列名称 direct_queue
    public void processDirectQueue(Message msg, Channel channel, MessageVo message) throws IOException {
        MessageProperties properties = msg.getMessageProperties();

        if(MessageType.TEMPLATE.name().equals(message.getType())) {//模板计数
            HabAppHabitTemplate template = habAppHabitTemplateMapperExt.selectByPrimaryKey(message.getId());
            if(template != null) {
                if(template.getUsedCount() != null) {
                    template.setUsedCount(template.getUsedCount() + 1);
                } else {
                    template.setUsedCount(1l);
                }
                this.habAppHabitTemplateMapperExt.updateByPrimaryKeySelective(template);
            }
        }

//        发布的每一条消息都会获得一个唯一的deliveryTag，
//        (任何channel上发布的第一条消息的deliveryTag为1，此后的每一条消息都会加1)，
//        deliveryTag在channel范围内是唯一的
        long tag = properties.getDeliveryTag();
//        这里multiple参数的含义是
//        true:将小于该tag值的消息确认
//        falase:只确认当前消息
        channel.basicAck(tag, false);
//        出错了，通知MQ把消息塞回的队列头部（不是尾部）
//        channel.basicNack(tag,false,true);
    }
}
