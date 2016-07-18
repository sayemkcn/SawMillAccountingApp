package sayem.toracode.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import sayem.toracode.entities.CategoryEntity;
import sayem.toracode.entities.ProductEntity;
import sayem.toracode.pojo.ProductProperties;

@Embeddable
public class InvoiceProduct {
	private String serial;
	private String type;
	@Transient
	private String categoryName;
	@OneToOne
	private CategoryEntity category;
	@Embedded
	private ProductProperties productProperties;
	private long purchasePrice;
	private long sellPrice;
	private String status;
	private String note;

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ProductProperties getProductProperties() {
		return productProperties;
	}

	public void setProductProperties(ProductProperties productProperties) {
		this.productProperties = productProperties;
	}

	public long getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(long purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public long getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(long sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public static List<InvoiceProduct> copyList(List<ProductEntity> sellingProductList) {
		List<InvoiceProduct> productList = new ArrayList<>();
		for (ProductEntity productEntity : sellingProductList) {
			InvoiceProduct product = new InvoiceProduct();
			product.setCategory(productEntity.getCategory());
			product.setCategoryName(productEntity.getCategoryName());
			product.setNote(productEntity.getNote());
			product.setProductProperties(productEntity.getProductProperties());
			product.setPurchasePrice(productEntity.getPurchasePrice());
			product.setSellPrice(productEntity.getSellPrice());
			product.setSerial(productEntity.getSerial());
			product.setStatus(productEntity.getStatus());
			product.setType(productEntity.getType());
			productList.add(product);
		}
		return productList;
	}

}
