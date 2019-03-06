package com.yangya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yangya.producer.FanoutProducer;
import com.yangya.service.OrderService;

@RestController
public class ProducerController {
	@Autowired
	private FanoutProducer fanoutProducer;
	
	@Autowired
	private OrderService orderService;

	@RequestMapping("/sendFanout")
	public String sendFanout(String queueName) {
		fanoutProducer.send(queueName);
		return "success";
	}
	
	@RequestMapping("/addOrder")
	public String addOrder(String queueName) {
		return orderService.addOrderAndDispatch();
		//return "success";
	}
}
