package com.gof.microservice.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gof.microservice.exception.ApiError;

import com.gof.microservice.models.Product;

@Component
public class ProductValidator implements Validator {
	
	@Autowired
	ApiError apiError=new ApiError();

	@Override
	public boolean supports(Class<?> clazz) {
		
		return clazz.equals(Product.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
	
		Product p =(Product)target;
		
		if(StringUtils.isEmpty(p.getProdId().toString())) {
			errors.reject("prodId");
			apiError.getValidationError().add("Please Provide product Id");
			
		}
		if(StringUtils.isEmpty(p.getProdCode().toString())) {
			errors.reject("prodCode");
			apiError.getValidationError().add("Please Provide Product Code ");
			
		}
		if(StringUtils.isEmpty(p.getProdCost().toString())) {
			errors.reject("prodCost");
			apiError.getValidationError().add("Please Provide Product Cost");
			
		}
		
	}


	
	
	

}
