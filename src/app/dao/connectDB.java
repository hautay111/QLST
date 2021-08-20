package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import app.model.Account1;
import app.model.Bill;
import app.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;;

public class connectDB {
	Connection conn = null;
    public static Connection ConnectDb(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/qlst","root","");
           // JOptionPane.showMessageDialog(null, "Connection Established");
            System.out.println("ket noi database thanh cong");
            return conn;
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    
    }
    
    public static void main(String[] args) {
    	// TODO Auto-generated method stub
    	Connection conn=connectDB.ConnectDb();
    	
    }
    public static ObservableList<Bill> getDatausers_bill() {
        Connection conn = ConnectDb();
        ObservableList<Bill> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select product.pro_name,brand.brand_name ,product.pro_sale_price,product.barcode from product INNER JOIN brand ON product.brand_id = brand.brand_id");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Bill(rs.getString("pro_name"),rs.getString("brand_name"),rs.getString("pro_sale_price"),rs.getString("barcode")));       
            }
        } catch (Exception e) {
        	System.out.println(e);
        }
        return list;
	}
//---------------------account-------------------------------
    public static ObservableList<Account1> getDataAccount1() {
        Connection conn = ConnectDb();
        ObservableList<Account1> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT employee.emp_id,employee.emp_name,employee.emp_email,employee.emp_phone,employee.emp_address,employee.emp_gender,employee.emp_user,employee.emp_pass,employee.emp_status,title.title_name FROM employee,title WHERE employee.title_id=title.title_id");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Account1(
                		Integer.parseInt(rs.getString("emp_id")),
                		Integer.parseInt(rs.getString("emp_status")),
                		rs.getString("emp_name"), 
                		rs.getString("emp_email"),
                		rs.getString("emp_phone"), 
                		rs.getString("emp_address"),  
                		rs.getString("emp_gender"),
                		rs.getString("title_name"),
                		rs.getString("emp_user"), 
                		rs.getString("emp_pass")
                	));    
                
            }
        } catch (Exception e) {
        	System.out.println(e);
        }
        return list;

    }
    
    public static ObservableList<Product> getDataProduct(){
        Connection conn = ConnectDb();
        ObservableList<Product> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select product.pro_id, product.pro_name,product.pro_expiry,product.pro_unit,brand.brand_name ,product.pro_sale_price,product.barcode from product INNER JOIN brand ON product.brand_id = brand.brand_id");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Product(Integer.parseInt(rs.getString("pro_id")),rs.getString("barcode"),rs.getString("pro_name"),rs.getString("pro_sale_price"),rs.getString("pro_expiry"),rs.getString("pro_unit"),rs.getString("brand_name")));       
            }
        } catch (Exception e) {
        	System.out.println(e);
        }
        return list;
    }

  
}
