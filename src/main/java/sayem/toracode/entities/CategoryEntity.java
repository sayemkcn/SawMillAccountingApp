package sayem.toracode.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name="category")
public class CategoryEntity extends BaseEntity {
	@Column(nullable=false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
