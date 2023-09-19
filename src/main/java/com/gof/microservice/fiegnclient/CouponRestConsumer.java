package com.gof.microservice.fiegnclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gof.microservice.models.Coupon;

@FeignClient("COUPON-SERVICE")
public interface CouponRestConsumer {
	
	@GetMapping("/coupon/getByCode/{code}")
	public Coupon getCouponByCode(@PathVariable String code) ;

	
	@GetMapping("/coupon/check/{code}")
	public String checkExpiredOrNot(@PathVariable String code) ;

	
}
