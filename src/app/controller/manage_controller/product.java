package app.controller.manage_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import app.controller.homepage.Home_Manage;
import app.controller.manage_controller.product_crud.*;
import javax.swing.JOptionPane;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Object;

import app.dao.connectDB;
import app.model.Category1;
import app.model.Product;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class product implements Initializable{
    @FXML
    private TableView<Product> table_product;
    @FXML
    private TableColumn<Product, Integer> col_product_id;

    @FXML
    private TableColumn<Product, String> col_product_name;

    @FXML
    private TableColumn<Product, String> col_product_price;

    @FXML
    private TableColumn<Product, String> col_product_amount;
    
    @FXML
    private TableColumn<Product, String> col_product_barcode;

    @FXML
    private TableColumn<Product, String> col_product_expiry;

    @FXML
    private TableColumn<Product, String> col_product_unit;

    @FXML
    private TableColumn<Product, String> col_product_brand;
    

    @FXML
    private TableColumn<Product, String> col_product_category;


    @FXML
    private TableColumn<Product, Integer> col_product_number;
    
    @FXML
    private TextField text_product_id;
    @FXML
    private TextField text_product_name;

    @FXML
    private TextField text_product_price;
    
    @FXML
    private TextField text_product_expiry;

    @FXML
    private TextField text_product_brand;

    @FXML
    private TextField text_product_unit;

    @FXML
    private TextField search_user_product;
    

    @FXML
    private TextField text_product_barcode;

    @FXML
    private TextField text_product_category;
    

    @FXML
    private Label label_amount;
    

    @FXML
    private TextField test;
    
    int index = -1;
    
    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    ObservableList<Product> listM;
    ObservableList<Product> dataList;
    
    @FXML
    private ComboBox<String> myComboBox;
    
    public void initialize(URL url, ResourceBundle rb) {
//    UpdateTable_product();
    search_user_product();
    showamount();

    
    // Code Source in description
    
//    DecimalFormat formatter = new DecimalFormat("###,###,###");
//    
//    double money = Double.parseDouble(text_product_price.getText()); 
//    
//    String moneyString = formatter.format(money);
//    System.out.println(moneyString);
       
    
    } 
    ObservableList<Category1> listM1;
    ObservableList<Category1> dataList1;
   
    
//    @FXML
//    void home(MouseEvent event) throws SQLException, IOException {
//    	Connection conn=connectDB.ConnectDb();
//		pst = conn.prepareStatement("select * from employee where emp_id=?");
//		pst.setString(1,a1);
//		ResultSet rs = pst.executeQuery();
//					
//		
//		if (rs.next()) {
//				System.out.println("dang nhap thanh cong");
//				Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
//				FXMLLoader loader=new FXMLLoader();
//				loader.setLocation(getClass().getResource("../../ui/manage/product/Product_sales.fxml"));								
//				Parent parent=loader.load();
//				Scene scene=new Scene(parent);				
//				stage.setScene(scene);			
//			}else{
//        		JOptionPane.showMessageDialog(null, "Usename or Password Blank.");	
//        	}		
//    }
    
    
   void showamount() {
	   conn = connectDB.ConnectDb();
        try {
        String sql ="SELECT COUNT(*) FROM product WHERE pro_id > 1";
        ResultSet rs = conn.createStatement().executeQuery(sql); 
			if (rs.next()) {
				int i = rs.getInt(1); // access first column in result
				    System.out.println(i);
				    label_amount.setText("Total Product: "+i);
				}
		} catch (SQLException e) {
			System.out.println(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
    
    
    @FXML
    void search_user_product(){
    	
//    	DecimalFormat formatter = new DecimalFormat("###,###,###");
//    	int a = 123;
//    	System.out.println(formatter.format(a)+" VNĐ");
    	
    	col_product_number.setCellValueFactory(new PropertyValueFactory<Product,Integer>("no"));
    	col_product_id.setCellValueFactory(new PropertyValueFactory<Product,Integer>("id"));
    	col_product_barcode.setCellValueFactory(new PropertyValueFactory<Product,String>("barcode"));
    	col_product_name.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
    	col_product_price.setCellValueFactory(new PropertyValueFactory<Product,String>("price"));
    	col_product_expiry.setCellValueFactory(new PropertyValueFactory<Product,String>("expiry"));
    	col_product_unit.setCellValueFactory(new PropertyValueFactory<Product,String>("unit"));
    	col_product_brand.setCellValueFactory(new PropertyValueFactory<Product,String>("brand"));
    	col_product_category.setCellValueFactory(new PropertyValueFactory<Product,String>("category"));

	           dataList = connectDB.getDataProduct();
	           table_product.setItems(dataList);
	           FilteredList<Product> filteredData = new FilteredList<>(dataList, b -> true);  
	           search_user_product.textProperty().addListener((observable, oldValue, newValue) -> {
	    filteredData.setPredicate(person -> {
	       if (newValue == null || newValue.isEmpty()) {
	        return true;
	       }    
	       String lowerCaseFilter = newValue.toLowerCase();
	       
	       if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
	        return true; // Filter matches name
	       } else if (person.getUnit().toLowerCase().indexOf(lowerCaseFilter) != -1) {
	        return true; // Filter matches unit
	       }else if (person.getBarcode().toLowerCase().indexOf(lowerCaseFilter) != -1) {
	        return true; // Filter matches barcode
	       }else if (person.getCategory().toLowerCase().indexOf(lowerCaseFilter) != -1) {
		        return true; // Filter matches category
	       }else if (String.valueOf(person.getBrand()).indexOf(lowerCaseFilter)!=-1)
	            return true;// Filter matches brand
	                                   
	            else  
	             return false; // Does not match.
	      });
	     });  
	     SortedList<Product> sortedData = new SortedList<>(filteredData);  
	     sortedData.comparatorProperty().bind(table_product.comparatorProperty()); 
	     table_product.setItems(sortedData);      

    }    	
    
    
    
    
    
    

    public void UpdateTable_product(){
   
    	col_product_number.setCellValueFactory(new PropertyValueFactory<Product,Integer>("no"));
    	col_product_id.setCellValueFactory(new PropertyValueFactory<Product,Integer>("id"));
    	col_product_barcode.setCellValueFactory(new PropertyValueFactory<Product,String>("barcode"));
    	col_product_name.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
    	col_product_price.setCellValueFactory(new PropertyValueFactory<Product,String>(("price")));
    	col_product_expiry.setCellValueFactory(new PropertyValueFactory<Product,String>("expiry"));
    	col_product_unit.setCellValueFactory(new PropertyValueFactory<Product,String>("unit"));
    	col_product_brand.setCellValueFactory(new PropertyValueFactory<Product,String>("brand"));
    	col_product_category.setCellValueFactory(new PropertyValueFactory<Product,String>("category"));
    	
        
        listM = connectDB.getDataProduct();
        table_product.setItems(listM);
    }

    @FXML
    void getSelected_product(MouseEvent event) {
        index = table_product.getSelectionModel().getSelectedIndex();
        if (index <= -1){
        
            return;
        }
        
        text_product_id.setText(col_product_id.getCellData(index).toString());
        text_product_barcode.setText(col_product_barcode.getCellData(index).toString());
        text_product_name.setText(col_product_name.getCellData(index).toString());
        text_product_price.setText(col_product_price.getCellData(index).toString());
        text_product_expiry.setText(col_product_expiry.getCellData(index).toString());
        text_product_unit.setText(col_product_unit.getCellData(index).toString());
        text_product_brand.setText(col_product_brand.getCellData(index).toString());
        text_product_category.setText(col_product_category.getCellData(index).toString());
 
    }

    @FXML
    void btn_product_reset() {
    	text_product_id.setText("");
    	text_product_name.setText("");
    	text_product_price.setText("");
    	text_product_expiry.setText("");
    	text_product_unit.setText("");
    	text_product_brand.setText("");
    	text_product_barcode.setText("");
    	text_product_category.setText("");
    	UpdateTable_product();
    }
		 
		


    @FXML
    void product_add(MouseEvent event) {
	    try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../ui/manage/product/crud_product.fxml"));
	                Parent root = (Parent) fxmlLoader.load();
	                Stage stage = new Stage();
	                stage.setScene(new Scene(root));  
	                stage.show();             
	                UpdateTable_product();
	                showamount();
	                search_user_product();
	                
	        } catch(Exception e) {
	        	
	           e.printStackTrace();
	          }
    }

    @FXML
    void product_delete(MouseEvent event) {
        conn = connectDB.ConnectDb();
        String sql = "delete from product where pro_id = ?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, text_product_id.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Delete");
                UpdateTable_product();
                search_user_product();
                btn_product_reset();
                showamount();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
    }
    
    @FXML
    void product_edit(MouseEvent event) {
        try {
        	
//            DecimalFormat formatter = new DecimalFormat("###,###,###");           
//            double money = Double.parseDouble(text_product_price.getText());            
//            String moneyString = formatter.format(money);
//            System.out.println(moneyString);
        	
        	
            conn = connectDB.ConnectDb();
            String value1 = text_product_id.getText();
            String value2 = text_product_name.getText();
            String value3 = text_product_price.getText();
            String value4 = text_product_expiry.getText();
            String value5 = text_product_unit.getText();
            String value6 = text_product_brand.getText();
            String value7 = text_product_category.getText();

            String sql = "update product set pro_name= '"+value2+"',pro_sale_price= '"+
            		value3+"',pro_expiry= '"+value4+"',pro_unit= '"+value5+"',pro_brand= '"+value6+"',pro_category= '"+value7+"' where pro_id = '"+value1+"' ";
            pst= conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Update");
//            UpdateTable_product();
            search_user_product();
            btn_product_reset();
            showamount();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    @FXML
    void load_product(MouseEvent event) throws SQLException {
    	UpdateTable_product();
//    	search_user_product();
    	text_product_id.setText("");
    	text_product_name.setText("");
    	text_product_price.setText("");
    	text_product_expiry.setText("");
    	text_product_unit.setText("");
    	text_product_brand.setText("");
    	text_product_barcode.setText("");
    	text_product_category.setText("");
    	
		Connection conn=connectDB.ConnectDb();
		ResultSet rs = pst.executeQuery();		
		rs.next();
    }

     
}
