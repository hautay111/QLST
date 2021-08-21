package app.model;

import java.text.DecimalFormat;

public class Product {

	int id,no;
	String price,barcode,name,expiry,unit,brand,category;
	
	public Product(int id, int no, String price, String barcode, String name, String expiry, String unit, String brand,
			String category) {
		this.id = id;
		this.no = no;
		this.price = price;
		this.barcode = barcode;
		this.name = name;
		this.expiry = expiry;
		this.unit = unit;
		this.brand = brand;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
	
}
