package sayem.toracode.entities;

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
	@OneToOne
	private CategoryEntity category;
	@Embedded
	private ProductProperties productProperties;
	private long purchasePrice;
	private long sellPrice;
	private String status;
	private String note;
	@ManyToOne
	private InvoiceEntity invoice;

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

	public InvoiceEntity getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceEntity invoice) {
		this.invoice = invoice;
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

}
