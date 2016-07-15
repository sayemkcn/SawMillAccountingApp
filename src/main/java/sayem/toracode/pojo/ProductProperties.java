package sayem.toracode.pojo;

import javax.persistence.Embeddable;

@Embeddable
public class ProductProperties {
	private String name;
	private double length;
	private double radious;
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
	public double getRadious() {
		return radious;
	}
	public void setRadious(double radious) {
		this.radious = radious;
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
	
	
	
}
