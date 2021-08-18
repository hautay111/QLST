package app.controller.manage_controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import app.dao.connectDB;
import app.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    private TableColumn<Product, String> col_product_expiry;

    @FXML
    private TableColumn<Product, String> col_product_unit;

    @FXML
    private TableColumn<Product, String> col_product_brand;

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
    int index = -1;
    
    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    ObservableList<Product> listM;
    ObservableList<Product> dataList;
    public void initialize(URL url, ResourceBundle rb) {
    UpdateTable_product();
    search_user_product();
    showamount();
    product_combobox();
    // Code Source in description
    } 
    
    
    @FXML
    private ComboBox<String> combobox_product;

    @FXML
    void Select_combobox(ActionEvent event) {
        String s = combobox_product.getSelectionModel().getSelectedItem().toString();
//        label.setText(s);
    } 
    public void product_combobox() {
      ObservableList<String> list1 = FXCollections.observableArrayList("JavaFX","SceneBuilder","Laravel","Python");
      combobox_product.setItems(list1);
    }
    
   void showamount() {
	   conn = connectDB.ConnectDb();
        try {
        String sql ="SELECT COUNT(*) FROM leader WHERE lead_id =1";
        ResultSet rs = conn.createStatement().executeQuery(sql); 
			if (rs.next()) {
				int i = rs.getInt(1); // access first column in result
				    System.out.println(i);
				}
		} catch (SQLException e) {
			System.out.println(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
    
    
    @FXML
    void btn_product_add(ActionEvent event) {
        conn = connectDB.ConnectDb();
        String sql = "insert into product (product_name,product_price,product_amount)values(?,?,?)";
        try {
        	

            pst = conn.prepareStatement(sql);
            pst.setString(1, text_product_name.getText());
            pst.setString(2, text_product_price.getText());
            pst.setString(3, text_product_expiry.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Users Add succes");
            UpdateTable_product();
            search_user_product();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    @FXML
    void search_user_product(){
    	col_product_id.setCellValueFactory(new PropertyValueFactory<Product,Integer>("id_product"));
    	col_product_name.setCellValueFactory(new PropertyValueFactory<Product,String>("name_product"));
    	col_product_price.setCellValueFactory(new PropertyValueFactory<Product,String>("price_product"));
    	col_product_amount.setCellValueFactory(new PropertyValueFactory<Product,String>("amount_product"));

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
	       } else if (person.getPrice().toLowerCase().indexOf(lowerCaseFilter) != -1) {
	        return true; // Filter matches email
	       }else if (person.getBarcode().toLowerCase().indexOf(lowerCaseFilter) != -1) {
	        return true; // Filter matches phone
	       }
	       else if (String.valueOf(person.getBrand()).indexOf(lowerCaseFilter)!=-1)
	            return true;// Filter matches username
	                                   
	            else  
	             return false; // Does not match.
	      });
	     });  
	     SortedList<Product> sortedData = new SortedList<>(filteredData);  
	     sortedData.comparatorProperty().bind(table_product.comparatorProperty());  
	     table_product.setItems(sortedData);      
	       
    }
    public void UpdateTable_product(){
    	col_product_id.setCellValueFactory(new PropertyValueFactory<Product,Integer>("id"));
    	col_product_name.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
    	col_product_price.setCellValueFactory(new PropertyValueFactory<Product,String>("price"));
    	col_product_amount.setCellValueFactory(new PropertyValueFactory<Product,String>("amount_product"));

        
        listM = connectDB.getDataProduct();
        table_product.setItems(listM);
    }

    @FXML
    void btn_product_delete(ActionEvent event) {
        conn = connectDB.ConnectDb();
        String sql = "delete from product where product_id = ?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, text_product_id.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Delete");
                UpdateTable_product();
                search_user_product();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
    }

    @FXML
    void btn_product_edit(ActionEvent event) {
        try {
            conn = connectDB.ConnectDb();
            String value1 = text_product_id.getText();
            String value2 = text_product_name.getText();
            String value3 = text_product_price.getText();
            String value4 = text_product_expiry.getText();

            String sql = "update product set product_name= '"+value2+"',product_price= '"+
                    value3+"',product_amount= '"+value4+"' where product_id = '"+value1+"' ";
            pst= conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Update");
            UpdateTable_product();
            search_user_product();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    @FXML
    void getSelected_product(MouseEvent event) {
        index = table_product.getSelectionModel().getSelectedIndex();
        if (index <= -1){
        
            return;
        }
        text_product_id.setText(col_product_id.getCellData(index).toString());
        text_product_name.setText(col_product_name.getCellData(index).toString());
        text_product_price.setText(col_product_price.getCellData(index).toString());
        text_product_expiry.setText(col_product_amount.getCellData(index).toString());
 
    }

    @FXML
    void btn_product_reset(ActionEvent event) {
    	text_product_id.setText("");
    	text_product_name.setText("");
    	text_product_price.setText("");
    	text_product_expiry.setText("");

    	UpdateTable_product();
    }
 
}
