package app.controller.manage_controller.product_crud;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import app.dao.connectDB;
import app.model.Category1;
import app.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class product_add implements Initializable{

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

	    
	    int index = -1;
	    
	    Connection conn =null;
	    ResultSet rs = null;
	    PreparedStatement pst = null;
	    ObservableList<Product> listM;
	    ObservableList<Product> dataList;
	    
	    
	    public void initialize(URL url, ResourceBundle rb) {
	        product_combobox();
//	        showamount();
	        // Code Source in description
	        } 
	        ObservableList<Category1> listM1;
	        ObservableList<Category1> dataList1;
	        @FXML
	        private ComboBox<String> combobox_product;
	        @FXML
	        void Select_combobox(ActionEvent event) {
	            String s = combobox_product.getSelectionModel().getSelectedItem().toString();
//	            label.setText(s);
	        } 
	        @FXML
	        private TextField test;
	        private ObservableList<String> stationsList = FXCollections.observableArrayList();
	        
	        public void product_combobox() {
//	          ObservableList<String> list1 = FXCollections.observableArrayList("cat_name");
//	          combobox_product.setItems(list1);
	        	
	            String sql = " select * from category ";

	            try {
	                conn = (Connection) connectDB.ConnectDb();
	                PreparedStatement pstStn = conn.prepareStatement(sql);
	                ResultSet stnRS = pstStn.executeQuery(sql);

	                while (stnRS.next()) {

//	                	combobox_product.getItems().add(stnRS.getString("cat_name"));

	                    stationsList.add(stnRS.getString("cat_name"));
	                    combobox_product.setItems(stationsList);
	                }
	                	
	                } catch (SQLException ex) {
	                    System.err.println("ERR" + ex);
	                }
	      } 
	    
	    
	    
	    @FXML
	    void btn_product_add(ActionEvent event) {
	        conn = connectDB.ConnectDb();
	        String sql = "insert into product (barcode,pro_name,pro_sale_price,pro_expiry,pro_unit)values(?,?,?,?,?)";
	        try {
	        	

	            pst = conn.prepareStatement(sql);
	            pst.setString(1, text_product_barcode.getText());
	            pst.setString(2, text_product_name.getText());
	            pst.setString(3, text_product_price.getText());
	            pst.setString(4, text_product_expiry.getText());
	            pst.setString(5, text_product_unit.getText());
	            pst.execute();
	            JOptionPane.showMessageDialog(null, "Users Add succes");
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, e);
	        }
	    }
	
}
