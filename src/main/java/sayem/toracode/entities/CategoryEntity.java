package sayem.toracode.entities;

import javax.persistence.Entity;

@Entity(name="category")
public class CategoryEntity extends BaseEntity {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
