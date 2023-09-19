package com.gof.microservice.service;



import java.util.List;



import com.gof.microservice.models.Product;

public interface ProductService {
	
	public Product saveProduct(Product product);

	public Product updateProduct(Product product);

	public Product getProductById(Integer id);
	
	public void deleteById(Integer id);
	
	public List<Product> getAllProduct();
	
	public boolean isExist(Integer id);
	
	public Double calculateDiscount(Double cost,Double disc);
	
	

}

