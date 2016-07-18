package sayem.toracode.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import sayem.toracode.pojo.InvoiceProduct;

@Entity(name = "invoices")
public class InvoiceEntity extends BaseEntity {
	@ElementCollection
	private List<InvoiceProduct> productList = new ArrayList<>();
	private long discount;
	private String status;

	public long getDiscount() {
		return discount;
	}

	public void setDiscount(long discount) {
		this.discount = discount;
	}

	public List<InvoiceProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<InvoiceProduct> productList) {
		this.productList = productList;
	}
	
	public Long getTotalPrice(List<InvoiceProduct> productList){
		long price = 0;
		for(InvoiceProduct product: productList){
			price+=product.getSellPrice();
		}
		return price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
