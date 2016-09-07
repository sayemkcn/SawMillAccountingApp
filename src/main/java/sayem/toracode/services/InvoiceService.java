package sayem.toracode.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sayem.toracode.entities.BusinessPartnerEntity;
import sayem.toracode.entities.InvoiceEntity;
import sayem.toracode.entities.ProductEntity;
import sayem.toracode.pojo.ProductProperties;
import sayem.toracode.repositories.InvoiceRepository;

@Service
public class InvoiceService {

	@Autowired
	private ProductService productService;
	@Autowired
	private InvoiceRepository invoiceRepository;

	public long calculatePrice(List<ProductEntity> productList, long discount) {
		long price = 0;
		for (ProductEntity product : productList) {
			price += (long) (product.getProductProperties().getSize(product.getType())
					* product.getProductProperties().getRate());
		}
		return price - discount;
	}

	public List<ProductEntity> calculateRemainingProducts(List<ProductEntity> sellingProductList) {
		List<ProductEntity> productList = new ArrayList<>();
		for (ProductEntity sellingProduct : sellingProductList) {
			// find original item by selling product id
			ProductEntity product = productService.findById(sellingProduct.getId());
			// subtract selling product property items for remaining product
			ProductProperties properties = product.getProductProperties();
			ProductProperties sProperties = sellingProduct.getProductProperties();
			properties.setHeight(properties.getHeight() - sProperties.getHeight());
			properties.setWidth(properties.getWidth() - sProperties.getWidth());
			properties.setLength(properties.getLength() - sProperties.getLength());
			properties.setPerimeter(properties.getPerimeter() - sProperties.getPerimeter());
			System.out.println(properties.toString());
			product.setProductProperties(properties);
			if (properties.getLength() <= 0 && properties.getPerimeter() <= 0 && properties.getHeight() <= 0
					&& properties.getWidth() <= 0) {
				product.setStatus("Sold Out");
			} else {
				product.setStatus("Partially Sold");
			}
			productList.add(product);
		}
		return productList;
	}

	public InvoiceEntity saveInvoice(InvoiceEntity invoice) {
		return invoiceRepository.saveAndFlush(invoice);
	}

	public InvoiceEntity findById(Long id) {
		return invoiceRepository.findOne(id);
	}

	public List<InvoiceEntity> findAll() {
		return invoiceRepository.findAll();
	}
	public List<InvoiceEntity> findByPartner(BusinessPartnerEntity partner){
		return invoiceRepository.findByBusinessPartner(partner);
	}
}
