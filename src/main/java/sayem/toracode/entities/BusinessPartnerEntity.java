package sayem.toracode.entities;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name="business_partner")
public class BusinessPartnerEntity extends BaseEntity {
	@Transient
	public static String PARTNER_TYPE_CUSTOMER = "customer";
	@Transient
	public static String PARTNER_TYPE_SUPPLIER = "supplier";
	
	private String name;
	private String address;
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "BusinessPartner [name=" + name + ", address=" + address + ", type=" + type + "]";
	}

}
