package app.model;

public class Account {
	int id_emp,emp_status;
	String emp_name,emp_gender,emp_email,emp_address,emp_phone,emp_user,emp_pass,title;
	
	public Account(int id_emp, int emp_status, String emp_name, String emp_gender, String emp_email, String emp_address,
			String emp_phone, String emp_user, String emp_pass, String title) {
		this.id_emp = id_emp;
		this.emp_status = emp_status;
		this.emp_name = emp_name;
		this.emp_gender = emp_gender;
		this.emp_email = emp_email;
		this.emp_address = emp_address;
		this.emp_phone = emp_phone;
		this.emp_user = emp_user;
		this.emp_pass = emp_pass;
		this.title = title;
	}

	public int getId_emp() {
		return id_emp;
	}

	public void setId_emp(int id_emp) {
		this.id_emp = id_emp;
	}

	public int getEmp_status() {
		return emp_status;
	}

	public void setEmp_status(int emp_status) {
		this.emp_status = emp_status;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getEmp_gender() {
		return emp_gender;
	}

	public void setEmp_gender(String emp_gender) {
		this.emp_gender = emp_gender;
	}

	public String getEmp_email() {
		return emp_email;
	}

	public void setEmp_email(String emp_email) {
		this.emp_email = emp_email;
	}

	public String getEmp_address() {
		return emp_address;
	}

	public void setEmp_address(String emp_address) {
		this.emp_address = emp_address;
	}

	public String getEmp_phone() {
		return emp_phone;
	}

	public void setEmp_phone(String emp_phone) {
		this.emp_phone = emp_phone;
	}

	public String getEmp_user() {
		return emp_user;
	}

	public void setEmp_user(String emp_user) {
		this.emp_user = emp_user;
	}

	public String getEmp_pass() {
		return emp_pass;
	}

	public void setEmp_pass(String emp_pass) {
		this.emp_pass = emp_pass;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
