package com.gof.microservice.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gof.microservice.models.Product;
import com.gof.microservice.repository.ProductRepository;
import com.gof.microservice.service.ProductService;



@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product saveProduct(Product product) {
		
		return productRepository.save(product);
	}

	@Override
	@Transactional
	@CachePut(value="product-cache",key="#product.prodId")
	public Product updateProduct(Product product) {
		
		return productRepository.save(product);
	}

	@Override
	@Transactional(readOnly=true)
	@CachePut(value="product-cache",key="#prodId")
	public Product getProductById(Integer prodId) {
		Optional<Product> findById = productRepository.findById(prodId);
		return findById.isPresent()?findById.get():null;
	}

	@Override
	@Transactional(readOnly=true)
	@CacheEvict(value="product-cache",key="#prodId")
	
	public void deleteById(Integer prodId) {
		productRepository.deleteById(prodId);
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Product> getAllProduct() {
		
		return productRepository.findAll();
	}

	@Override
	public boolean isExist(Integer id) {
		
		return productRepository.existsById(id);
	}

	@Override
	public Double calculateDiscount(Double cost, Double disc) {
		double d = 100.0;
		Double dCOst=cost*disc/d;
		Long round = Math.round(cost-dCOst);
		return round.doubleValue();
	}
	

}
