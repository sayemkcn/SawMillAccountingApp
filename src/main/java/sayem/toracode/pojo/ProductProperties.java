package sayem.toracode.pojo;

import javax.persistence.Embeddable;

@Embeddable
public class ProductProperties {
	private String name;
	private double length;
	private double perimeter;
	private double height;
	private double width;
	private int rate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getPerimeter() {
		return perimeter;
	}

	public void setPerimeter(double radious) {
		this.perimeter = radious;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getSize(String type) {
		if (type.equals("Round")) {
			return (this.perimeter * this.perimeter * this.length) / 2304;
		} else if (type.equals("Sized")) {
			return (this.height * length * width) / 144;
		}
		return 0;
	}

}
