package com.gof.microservice.models;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer couponId;
	
	private String couponCode;
	private Double couponDisc;
	
	private Date expDate;
	@JsonIgnore
	private boolean applied=true;
	
	

}

