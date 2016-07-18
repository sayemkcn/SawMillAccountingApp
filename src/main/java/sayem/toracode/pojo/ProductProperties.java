package sayem.toracode.pojo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductProperties {
	private double length;
	@Column(nullable=true)
	private double perimeter;
	@Column(nullable=true)
	private double height;
	@Column(nullable=true)
	private double width;
	private int rate;

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
		double size = 0;
		if (type.equals("Round")) {
			size= (this.perimeter * this.perimeter * this.length) / 2304;
		} else if (type.equals("Sized")) {
			size = (this.height * length * width) / 144;
		}
		return size;
	}
	
	public double getSizeRounded(String type) {
		double size = 0;
		if (type.equals("Round")) {
			size= (this.perimeter * this.perimeter * this.length) / 2304;
		} else if (type.equals("Sized")) {
			size = (this.height * length * width) / 144;
		}
		return this.round(size, 2);
	}
	
	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	@Override
	public String toString() {
		return "ProductProperties [length=" + length + ", perimeter=" + perimeter + ", height=" + height + ", width="
				+ width + ", rate=" + rate + "]";
	}
	
	
}
