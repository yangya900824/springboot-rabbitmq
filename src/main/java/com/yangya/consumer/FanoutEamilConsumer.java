package com.yangya.consumer;

import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;

@Component
public class FanoutEamilConsumer {
	@RabbitListener(queues = "fanout_email_queue")
	public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws Exception {
		String messageId = message.getMessageProperties().getMessageId();
		String msg = new String(message.getBody(), "UTF-8");
		System.out.println("邮件消费者获取生产者消息msg:" + msg + ",消息id:" + messageId);
		JSONObject jsonObject = JSONObject.parseObject(msg);
		Integer timestamp = jsonObject.getInteger("timestamp");
		try {
			int result = 1 / timestamp;
			System.out.println("result:" + result);
			// 通知mq服务器删除该消息
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			e.printStackTrace();
			// // 丢弃该消息(放入死信队列)
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
		}

	}

}
