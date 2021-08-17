package app.controller.manage_controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import app.dao.connectDB;
import app.model.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class account {
	
	Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    int index = -1;

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private TextField phone;

    @FXML
    private TextField address;

    @FXML
    private DatePicker dateofbirth;
    
    @FXML
    private DatePicker date_start;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private ComboBox<String> title;

    @FXML
    private TextField user;

    @FXML
    private TextField pass;

    @FXML
    private TextField id;

    @FXML
    private TextField search;
    
    @FXML
    private TableView<Account> table_account;

    @FXML
    private TableColumn<Account, Integer> col_id;

    @FXML
    private TableColumn<Account, String> col_name;

    @FXML
    private TableColumn<Account, String> col_email;

    @FXML
    private TableColumn<Account, String> col_phone;

    @FXML
    private TableColumn<Account, String> col_address;

    @FXML
    private TableColumn<Account, String> col_gender;

    @FXML
    private TableColumn<Account, String> col_title;

    @FXML
    private TableColumn<Account, String> col_user;

    @FXML
    private TableColumn<Account, String> col_pass;

    @FXML
    private TableColumn<Account, Integer> col_status;
    
    ObservableList<Account> listM;
    ObservableList<Account> dataList;
    
    @FXML
    void Add(ActionEvent event) {
    	conn = connectDB.ConnectDb();
        String sql = "insert into employee (emp_name,emp_email,emp_phone,emp_address,emp_birthday,emp_start_date,emp_gender,title_id,emp_user,emp_pass) "
        		+ "values(?,?,?,?,?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, name.getText());
            pst.setString(2, email.getText());
            pst.setString(3, phone.getText());
            pst.setString(4, address.getText());
            pst.setString(5, dateofbirth.getEditor().getText());
            pst.setString(6, date_start.getEditor().getText());
            pst.setString(7, gender.getValue());
            pst.setInt(8, title_id);
            pst.setString(9, user.getText());
            pst.setString(10, pass.getText());            
            pst.execute();
            JOptionPane.showMessageDialog(null, "Add successfully.");
            UpdateTable();
            search();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    @FXML
    void Delete(ActionEvent event) {
    	 conn = connectDB.ConnectDb();
    	    String sql = "delete from users where user_id = ?";
    	        try {
    	            pst = conn.prepareStatement(sql);
    	            pst.setString(1, id.getText());
    	            pst.execute();
    	            JOptionPane.showMessageDialog(null, "Delete");
    	            UpdateTable();
    	            search();
    	        } catch (Exception e) {
    	            JOptionPane.showMessageDialog(null, e);
    	        }
    }

    @FXML
    void Reset(ActionEvent event) {

    }

    @FXML
    void Update(ActionEvent event) {
    	try {
            conn = connectDB.ConnectDb();
            String value1 = id.getText();
            String value2 = name.getText();
            String value3 = email.getText();
            String value4 = phone.getText();
            String value5 = address.getText();
            String value6 = gender.getValue();
            Integer value7 = title_id;
            String value8 = user.getText();
            String sql = "update employee set emp_name= '"+value2+"',emp_email= '"+value3+"',emp_phone= '"+value4+"',emp_address= '"
            		+value5+"',emp_gender= '"+value6+"',title_id='"+value7+"',emp_user= '"+value8+"' where emp_id= '"+value1+"' ";
            pst= conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Update");
            
//            UpdateTable();
//            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void box_gender(ActionEvent event) {
    	
    }
    private static Integer title_id;
    @FXML
    void box_titlle(ActionEvent event) throws SQLException {
    	Connection con=connectDB.ConnectDb();
    	String title_name=title.getValue();
    	String sql="select * from title where title_name='"+title_name+"' ";
    	 PreparedStatement statement = con.prepareStatement(sql);
         ResultSet set = statement.executeQuery();
         if(set.next()) {
        	 title_id=set.getInt("title_id");
        	 System.out.println(title_id);
         }
    }
    
    ObservableList<String> list=FXCollections.observableArrayList("Male","Female");
    private List<String> getDataTitle() {


        // Define the data you will be returning, in this case, a List of Strings for the ComboBox
        List<String> options = new ArrayList<>();

        try {
        	Class.forName("com.mysql.jdbc.Driver");
            Connection con=connectDB.ConnectDb();
            String query = "select title_name from title";
            PreparedStatement statement = con.prepareStatement(query);

            ResultSet set = statement.executeQuery();

            while (set.next()) {
                options.add(set.getString("title_name"));
            }

            statement.close();
            set.close();

            // Return the List
            return options;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(app.Main.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @FXML
    void getSelected (MouseEvent event){
    index = table_account.getSelectionModel().getSelectedIndex();
    if (index <= -1){
    
        return;
    }
    id.setText(col_id.getCellData(index).toString());
    name.setText(col_name.getCellData(index).toString());
    email.setText(col_email.getCellData(index).toString());
    phone.setText(col_phone.getCellData(index).toString());
    address.setText(col_address.getCellData(index).toString());
//    gender.setValue(col_gender.getCellData(index).toString());
//    title.setValue(col_title.getCellData(index).toString());
    pass.setText(col_pass.getCellData(index).toString());
    user.setText(col_user.getCellData(index).toString());
    
    }
    
    public void UpdateTable(){
    	col_id.setCellValueFactory(new PropertyValueFactory<Account,Integer>("id_emp"));
    	col_title.setCellValueFactory(new PropertyValueFactory<Account,String>("title"));
    	col_name.setCellValueFactory(new PropertyValueFactory<Account,String>("emp_name"));
    	col_email.setCellValueFactory(new PropertyValueFactory<Account,String>("emp_email"));
    	col_phone.setCellValueFactory(new PropertyValueFactory<Account,String>("emp_phone"));
    	col_address.setCellValueFactory(new PropertyValueFactory<Account,String>("emp_address"));
    	col_gender.setCellValueFactory(new PropertyValueFactory<Account,String>("emp_gender"));
    	col_user.setCellValueFactory(new PropertyValueFactory<Account,String>("emp_user"));
    	col_pass.setCellValueFactory(new PropertyValueFactory<Account,String>("emp_pass"));
    	col_status.setCellValueFactory(new PropertyValueFactory<Account,Integer>("emp_status"));
        
        listM = connectDB.getDataAccount();
        table_account.setItems(listM);
    }
    void search(){
    	col_id.setCellValueFactory(new PropertyValueFactory<Account,Integer>("id_emp"));
    	col_title.setCellValueFactory(new PropertyValueFactory<Account,String>("title"));
    	col_name.setCellValueFactory(new PropertyValueFactory<Account,String>("emp_name"));
    	col_email.setCellValueFactory(new PropertyValueFactory<Account,String>("emp_email"));
    	col_phone.setCellValueFactory(new PropertyValueFactory<Account,String>("emp_phone"));
    	col_address.setCellValueFactory(new PropertyValueFactory<Account,String>("emp_address"));
    	col_gender.setCellValueFactory(new PropertyValueFactory<Account,String>("emp_gender"));
    	col_user.setCellValueFactory(new PropertyValueFactory<Account,String>("emp_user"));
    	col_pass.setCellValueFactory(new PropertyValueFactory<Account,String>("emp_pass"));
    	col_status.setCellValueFactory(new PropertyValueFactory<Account,Integer>("emp_status"));
	           
	           dataList = connectDB.getDataAccount();
	           table_account.setItems(dataList);
	           FilteredList<Account> filteredData = new FilteredList<>(dataList, b -> true);  
	           search.textProperty().addListener((observable, oldValue, newValue) -> {
	    filteredData.setPredicate(person -> {
	       if (newValue == null || newValue.isEmpty()) {
	        return true;
	       }    
	       String lowerCaseFilter = newValue.toLowerCase();
	       
	       if (person.getEmp_name().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
	        return true; // Filter matches name
	       } else if (person.getEmp_email().toLowerCase().indexOf(lowerCaseFilter) != -1) {
	        return true; // Filter matches email
	       } else if (person.getEmp_phone().toLowerCase().indexOf(lowerCaseFilter) != -1) {
		     return true; // Filter matches email
		   } else if (person.getEmp_user().toLowerCase().indexOf(lowerCaseFilter) != -1) {
		        return true; // Filter matches email
		   }else  
	             return false; // Does not match.
	      });
	     });  
	     SortedList<Account> sortedData = new SortedList<>(filteredData);  
	     sortedData.comparatorProperty().bind(table_account.comparatorProperty());  
	     table_account.setItems(sortedData);      
	       
    }
    
    @FXML
    private void initialize() {
        title.setItems(FXCollections.observableArrayList(getDataTitle()));
        gender.setItems(list);
        UpdateTable();
		search();
    }

}
