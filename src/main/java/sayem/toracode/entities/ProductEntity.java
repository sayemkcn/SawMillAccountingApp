package sayem.toracode.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import sayem.toracode.pojo.ProductProperties;

@Entity(name = "products")
public class ProductEntity extends BaseEntity {
	private String serial;
	private String type;
	@Transient
	private String categoryName;
	@OneToOne(cascade=CascadeType.ALL)
	private CategoryEntity category;
	@Embedded
	private ProductProperties productProperties;
	private long purchasePrice;
	private long sellPrice;
	private String status;
	private String note;
	@ManyToOne(cascade=CascadeType.ALL)
	private BusinessPartnerEntity businessPartner;

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

	@Override
	public String toString() {
		return "ProductEntity [serial=" + serial + ", type=" + type + ", categoryName=" + categoryName + ", category="
				+ category + ", productProperties=" + productProperties + ", purchasePrice=" + purchasePrice
				+ ", sellPrice=" + sellPrice + ", status=" + status + ", note=" + note + "]";
	}

	public BusinessPartnerEntity getBusinessPartner() {
		return businessPartner;
	}

	public void setBusinessPartner(BusinessPartnerEntity businessPartner) {
		this.businessPartner = businessPartner;
	}

	
}
