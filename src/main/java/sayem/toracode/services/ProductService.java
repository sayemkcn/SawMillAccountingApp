package sayem.toracode.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sayem.toracode.entities.ProductEntity;
import sayem.toracode.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	public List<ProductEntity> getAllProductsByType(String type) {
		List<ProductEntity> productList = new ArrayList<>();
		for (ProductEntity product : productRepository.findAll()) {
			if (product.getType().equals(type))
				productList.add(product);
		}
		return productList;
	}
	
	public List<ProductEntity> findProductByType(String type){
		return productRepository.findByType(type);
	}

	public List<ProductEntity> findAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	public ProductEntity findById(Long id) {
		return productRepository.findOne(id);
	}
	
	public List<ProductEntity> findBySerial(String serial){
		return productRepository.findBySerial(serial.toLowerCase());
	}
	
	public long calculatePrice(ProductEntity product){
		return (long) (product.getProductProperties().getSize(product.getType())*product.getProductProperties().getRate());
	}

	public void saveProductList(List<ProductEntity> remainingProductList) {
		productRepository.save(remainingProductList);
	}
}
