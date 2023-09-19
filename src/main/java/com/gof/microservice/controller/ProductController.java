package com.gof.microservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gof.microservice.exception.ProductNotFoundException;
import com.gof.microservice.exception.ValidationError;
import com.gof.microservice.fiegnclient.CouponRestConsumer;
import com.gof.microservice.models.Coupon;
import com.gof.microservice.models.Product;
//import com.gof.microservice.fiegnclient.CouponRestController;
import com.gof.microservice.service.ProductService;
import com.gof.microservice.validator.ProductValidator;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService service;
	
	@Autowired
	private CouponRestConsumer consumer;
	
	@Autowired
	private ProductValidator productValidator;
	@PostMapping("/save")
	public ResponseEntity<String > saveProduct(@RequestBody Product product,Errors errors){
		productValidator.validate(product, errors);
		if(!errors.hasErrors()) {
			String res=consumer.checkExpiredOrNot(product.getCoupon().getCouponCode());
			System.out.println(res);
			
			Coupon c =consumer.getCouponByCode(product.getCoupon().getCouponCode());
			System.out.println(c);
			if(c == null) {
				return new ResponseEntity<String>("Coupon Not Found",HttpStatus.OK);
				
			}else if (res.equals("Expired")) {
				return new ResponseEntity<String>("Coupon has been Expired !", HttpStatus.OK);
				
			} else {
				 Double couponDisc = c.getCouponDisc();
				if(product.getCoupon().isApplied()) {
					couponDisc=0.0;
				}
				Double prodCost = product.getProdCost();
				System.out.println(prodCost+","+couponDisc);
				product.setFinalCost(service.calculateDiscount(prodCost, couponDisc));
				service.saveProduct(product);
				return new ResponseEntity<String>("Product Has been Successfully Updated !",HttpStatus.OK);
			
				
			}
		}
		else {
			throw new ValidationError("Please provide Valid Details");
		}
	}
	
	@GetMapping("/apply")
	public ResponseEntity<Double > applyCoupon(Double cost,Double disc){
		return new ResponseEntity<Double>(service.calculateDiscount(cost, disc),HttpStatus.OK);
		
	}
	
	@GetMapping("/getOne/{id}")
//	@HystrixCommand(fallbackMethod = "getProductionException")
	public ResponseEntity<Product> getOneProduct(@PathVariable Integer id){
		Product productById = service.getProductById(id);
		System.out.println(productById);
		if(productById !=null) {
			return new ResponseEntity<Product>(productById,HttpStatus.OK);
		}else {
			throw new ProductNotFoundException("No Product Found");
		}
	}
	
	
	//fallBack methods 
	public ResponseEntity<Product> getProductionException(Integer id){
		throw new ProductNotFoundException("No Product Found");
		
	}
	
	public List<Product> getAllProduct(){
		return service.getAllProduct();
		
	}
	
	@PostMapping("/update")
	public ResponseEntity<String> UpdateProduct(@RequestBody Product product,Errors errors){
		if(service.isExist(product.getProdId())) {
			productValidator.validate(product, errors);
			if(!errors.hasErrors()) {
				service.saveProduct(product);
				return new ResponseEntity<String>("Product Has been Updated Successfully",HttpStatus.OK);
				
			}else {
				throw new ValidationError("Please Provide valid details");
				
			}
		}else {
			throw new ProductNotFoundException("No Product Found");
		}
	}
	
	public ResponseEntity<String > deleteProduct(@PathVariable Integer Id){
		if(service.isExist(Id)) {
			service.deleteById(Id);
		return new ResponseEntity<String>("Product Deleted Successfully",HttpStatus.OK);
		}else {
			throw new ProductNotFoundException("No Product Found");
		}
		
	}

}
