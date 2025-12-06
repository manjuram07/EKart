package com.infy.ekart.payment.service;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.infy.ekart.payment.dto.TransactionStatus;

@Service
public class PaymentCircuitBreakerService {
		
//	@Autowired
//	private RestTemplate template;

	@Autowired
	private WebClient webClient; 
	
	// Add necessary CircuitBreaker annotation
	@CircuitBreaker(name="paymentService", fallbackMethod = "paymentFailed")
	public void updateOrderAfterPayment(Integer orderId, String transactionStatus) {
//		template.put("http://customerMS/Ekart/customerorder-api/order/"+orderId+"/update/order-status", transactionStatus);
		
		webClient.put()
						.uri("http://customerMS/Ekart/customerorder-api/order/"+orderId+"/update/order-status")
						.retrieve()
						.bodyToMono(TransactionStatus.class);
	}

	public String paymentFailed(Exception ex){
		return "Payment Failed: "+ex.getMessage();
	}
}
