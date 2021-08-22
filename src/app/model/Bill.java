package app.model;

public class Bill {
	String name,brand,price,code,category;
	
	public Bill(String name,String brand, String price,String code,String category) {
		super();		
		this.name = name;	
		this.brand = brand;
		this.price = price;
		this.code = code;
		this.category = category;
		
	}

	
	
	
	public String getCategory() {
		return category;
	}




	public void setCategory(String category) {
		this.category = category;
	}




	public String getBrand() {
		return brand;
	}




	public void setBrand(String brand) {
		this.brand = brand;
	}




	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	
	
	

}
