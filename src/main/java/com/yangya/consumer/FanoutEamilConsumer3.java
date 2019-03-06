package com.yangya.consumer;

import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

//邮件队列
//@Component
public class FanoutEamilConsumer3 {
	@RabbitListener(queues = "fanout_email_queue")
	public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws Exception {
		System.out.println(Thread.currentThread().getName() + ",邮件消费者获取生产者消息msg:" + new String(message.getBody(), "UTF-8")
						+ ",messageId:" + message.getMessageProperties().getMessageId());
		// 手动ack
		Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
		// 手动签收
		channel.basicAck(deliveryTag, false);
	}
}
