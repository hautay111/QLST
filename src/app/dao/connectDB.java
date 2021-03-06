package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import app.model.Account1;
import app.model.Bill;

import app.model.Product;

import app.model.Brand1;
import app.model.Category1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;;

public class connectDB {
	Connection conn = null;
    public static Connection ConnectDb(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/project_qlst","root","");
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
            PreparedStatement ps = conn.prepareStatement("select * from product");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
<<<<<<< HEAD
<<<<<<< HEAD
                list.add(new Bill(rs.getRow(),rs.getString("pro_name"),rs.getString("pro_brand"),rs.getString("pro_sale_price"),rs.getString("barcode"),rs.getString("pro_category")));       
=======
                list.add(new Bill(rs.getRow(), rs.getString("pro_name"), rs.getString("pro_brand"), rs.getString("pro_sale_price"), rs.getString("barcode"), rs.getString("pro_category")));
//                rs.getRow(),rs.getString("pro_name"),rs.getString("pro_brand"),rs.getString("pro_sale_price"),rs.getString("barcode"),rs.getString("pro_category")
>>>>>>> 496538b8f440ac0e847f0c4dc0ba0e5044a8076a
=======
                list.add(new Bill(rs.getRow(), rs.getString("pro_name"), rs.getString("pro_brand"), rs.getString("pro_sale_price"), rs.getString("barcode"), rs.getString("pro_category")));
//                rs.getRow(),rs.getString("pro_name"),rs.getString("pro_brand"),rs.getString("pro_sale_price"),rs.getString("barcode"),rs.getString("pro_category")
>>>>>>> 496538b8f440ac0e847f0c4dc0ba0e5044a8076a
            }
        } catch (Exception e) {
        	System.out.println(e);
        }
        return list;
	}
    
    
    
    
//    public static ObservableList<Bill> getDatausers_bill() {
//        Connection conn = ConnectDb();
//        ObservableList<Bill> list = FXCollections.observableArrayList();
//        try {
//            PreparedStatement ps = conn.prepareStatement("select product.pro_name,product.pro_brand, brand.brand_name ,product.pro_sale_price,product.barcode from product INNER JOIN brand ON product.brand_id = brand.brand_id");
//            ResultSet rs = ps.executeQuery();
//            
//            while (rs.next()){   
//                list.add(new Bill(rs.getString("pro_name"),rs.getString("brand_name"),rs.getString("pro_sale_price"),rs.getString("barcode")));       
//            }
//        } catch (Exception e) {
//        	System.out.println(e);
//        }
//        return list;
//	}
//---------------------account-------------------------------
    public static ObservableList<Account1> getDataAccount1() {
        Connection conn = ConnectDb();
        ObservableList<Account1> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT employee.*,title.* FROM employee,title WHERE employee.title_id=title.title_id");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Account1(
                		Integer.parseInt(rs.getString("emp_id")),
                		Integer.parseInt(rs.getString("emp_status")),
                		rs.getRow(),
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
  //---------------------Category-brand-------------------------------
    public static ObservableList<Category1> getDataCategory1() {
        Connection conn = ConnectDb();
        ObservableList<Category1> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT category.* FROM category");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Category1(
                		Integer.parseInt(rs.getString("cat_id")), 
                		rs.getRow(),
                		rs.getString("cat_name")
                	));    
                
            }
        } catch (Exception e) {
        	System.out.println(e);
        }
        return list;
        
	}    
    public static ObservableList<Brand1> getDataBrand1() {
        Connection conn = ConnectDb();
        ObservableList<Brand1> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT brand.* FROM brand");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Brand1( 
                		Integer.parseInt(rs.getString("brand_id")),
                		rs.getRow(),
                		rs.getString("brand_name")
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
//            PreparedStatement ps = conn.prepareStatement("select product.pro_id, product.pro_name,product.pro_expiry,product.pro_unit,product.pro_category,product.pro_brand ,product.pro_sale_price,product.barcode from product INNER JOIN brand ON product.brand_id = brand.brand_id");
        	PreparedStatement ps = conn.prepareStatement("select * from product");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Product(Integer.parseInt(rs.getString("pro_id")),rs.getRow(),rs.getString("pro_sale_price"),rs.getString("barcode"),rs.getString("pro_name"),rs.getString("pro_expiry"),rs.getString("pro_unit"),rs.getString("pro_brand"),rs.getString("pro_category")));       
            }
        } catch (Exception e) {
        	System.out.println(e);
        }
        return list;
    }

 

    
    
}
