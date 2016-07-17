package sayem.toracode.services;

import java.util.List;

import org.springframework.stereotype.Service;

import sayem.toracode.entities.ProductEntity;

@Service
public class InvoiceService {

	public long calculatePrice(List<ProductEntity> productList, long discount) {
		long price = 0;
		for(ProductEntity product:productList){
			price += (long) (product.getProductProperties().getSize(product.getType())*product.getProductProperties().getRate());
		}
		return price-discount;
	}

}
