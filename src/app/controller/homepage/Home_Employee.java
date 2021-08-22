package app.controller.homepage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import app.controller.employee_controller.Bill_employee;
import app.dao.connectDB;
import app.model.Bill;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Home_Employee implements Initializable{
	
	
	
    @FXML
    private Label text_code;

    @FXML
    private Label text_name;

    @FXML
    private TextField text_amount;

    @FXML
    private Label text_type;

    @FXML
    private Label text_price;

    @FXML
    private Label text_total;

    @FXML
    private TableColumn<Bill,String> col_name;

    @FXML
    private TableColumn<Bill,String> col_barcode;
    @FXML
    private TableColumn<Bill,String> col_type;

    @FXML
    private TableColumn<Bill,String> col_price;

    @FXML
    private TableView<Bill> table_bill;
    
    @FXML
    private TextArea bill;
    
    @FXML
    private TextField search_bill;
    

    @FXML
    private Label ltotal;
    
    int index = -1;
    
    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    ObservableList<Bill> listM;
    ObservableList<Bill> dataList;
	
    
    @FXML
    private BorderPane mainPane;
   
	@FXML
    private Label id;
	
    @FXML
    private Label menu;

    @FXML
    private Label menucolse;

    @FXML
    private VBox slider;
	
	@FXML
    private Label user;

    @FXML
    private Label title;

    @FXML
    private AnchorPane pane1;

    @FXML
    private Pane pane2;
    

    @FXML
    private VBox home_lider;
    
    public void initialize(URL url, ResourceBundle rb) {
    	

        // Code Source in description
    } 
    
    
    
    
    
    @FXML
	void home(MouseEvent event) throws IOException {
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("../../ui/homepage/Home_Employee.fxml"));							
		Parent parent=loader.load();
        Home_Employee home=loader.getController();
        home.getId(a1,a2,a3);
		Scene scene=new Scene(parent);				
		stage.setScene(scene);
	}
	
	@FXML
	void exit(MouseEvent event) {
		try {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../../ui/homepage/Login.fxml"));
			Parent parent;

			parent = loader.load();

			Scene scene = new Scene(parent);
			stage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @FXML
    void bill_employee(MouseEvent event) {
        try {
            //add you loading or delays - ;-)
           Node node = (Node) event.getSource();
           Stage stage = (Stage) node.getScene().getWindow();                  
           stage.close();
           
           Parent root = FXMLLoader.load(getClass().getResource("/app/ui/employee/bill.fxml"));       
           Scene scene = new Scene(root);       
           stage.setScene(scene);
           stage.show();

        } catch (Exception ex) {
            System.out.println("y"+ex.getMessage());
        }

    }
	
	
    @FXML
    void inventory_employee(MouseEvent event) {
        try {
            //add you loading or delays - ;-)
           Node node = (Node) event.getSource();
           Stage stage = (Stage) node.getScene().getWindow();                  
           stage.close();
           
           Parent root = FXMLLoader.load(getClass().getResource("/app/ui/employee/inventory.fxml"));       
           Scene scene = new Scene(root);       
           stage.setScene(scene);
           stage.show();

        } catch (Exception ex) {
            System.out.println("y"+ex.getMessage());
        }

    }
    
    
	
	
    private static String emp_id, name,phone,email,username,title_name,date;
	@FXML
    void toInformation(MouseEvent event) throws IOException, SQLException {
		
		Connection conn=connectDB.ConnectDb();
		pst = conn.prepareStatement("select * from employee where emp_id=?");	
		pst.setString(1, a1);
		ResultSet rs = pst.executeQuery();
					
		
		if (rs.next()==true) {
				System.out.println("kiem tra thanh cong");
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(getClass().getResource("../../ui/homepage/Info_Employee.fxml"));
				Parent parent=loader.load();
				emp_id=rs.getString("emp_id");
				name=rs.getString("emp_name");
				phone=rs.getString("emp_phone");
				email=rs.getString("emp_email");
				username=rs.getString("emp_user");
				date=rs.getString("emp_birthday");
				int title_id=rs.getInt("title_id");
				pst = conn.prepareStatement("select * from title where title_id=?");	
				pst.setLong(1, title_id);
				ResultSet rs1 = pst.executeQuery();
				if (rs1.next()) {
					title_name=rs1.getString("title_name");
					System.out.println(title_name);
				}
				Info_Employee info=loader.getController();
				info.getInfo(emp_id, name, title_name, phone, email, username,date);
				mainPane.setCenter(parent);
			}else{
        		JOptionPane.showMessageDialog(null, "Ko do dc.");	
        	}
			conn.close();
	}
	
	

	@FXML
	void logout(MouseEvent event) throws SQLException, IOException {
		Connection conn=connectDB.ConnectDb();
		pst = conn.prepareStatement("update employee set emp_status=0 where emp_id='"+a1+"' ");
		pst.execute();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../../ui/homepage/Login.fxml"));
		Parent parent;
		parent = loader.load();
		Scene scene = new Scene(parent);
		stage.setScene(scene);
	}
	
	private static String a1,a2,a3;
	public void getId(String id1,String user1,String title1) {
		id.setText(id1);
		user.setText(user1);
		title.setText(title1);
		a1=id.getText();
		a2=user.getText();
		a3=title.getText();
		System.out.println(a1+" / "+a2+" / "+a3);
	}

	
	

	    

	    
	    
		
//			@FXML
//		void exit(MouseEvent event) {
//			try {
//				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//				FXMLLoader loader = new FXMLLoader();
//				loader.setLocation(getClass().getResource("../../ui/homepage/Home_Employee.fxml"));
//				Parent parent;
//
//				parent = loader.load();
//
//				Scene scene = new Scene(parent);
//				stage.setScene(scene);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		
	
}
