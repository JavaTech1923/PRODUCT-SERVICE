package com.gof.microservice.models;

import java.io.Serializable;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="product_micro")
public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer prodId;
	private String prodCode;
	private Double prodCost;
	
	private Double finalCost;
	
	@Transient
	private Coupon coupon;
	
	

}
