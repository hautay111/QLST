package app.controller.manage_controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import app.controller.homepage.*;
import app.dao.connectDB;
import app.model.Account1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class account1 implements Initializable {
	EncryptorAES encryptorAES = new EncryptorAES();

	Connection conn = null;
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
	private TextField txt_search;

	@FXML
	private TableView<Account1> table_account;

	@FXML
	private TableColumn<Account1, Integer> col_id;

	@FXML
	private TableColumn<Account1, String> col_name;

	@FXML
	private TableColumn<Account1, String> col_email;

	@FXML
	private TableColumn<Account1, String> col_phone;

	@FXML
	private TableColumn<Account1, String> col_address;

	@FXML
	private TableColumn<Account1, String> col_gender;

	@FXML
	private TableColumn<Account1, String> col_title;

	@FXML
	private TableColumn<Account1, String> col_user;

	@FXML
	private TableColumn<Account1, String> col_pass;

	@FXML
	private TableColumn<Account1, Integer> col_status;
	
	@FXML
    private TableColumn<Account1, Integer> col_no;

	ObservableList<Account1> listM;
	ObservableList<Account1> dataList;

//    ------------------------------------------------------------------------------------
	void reset() {
		id.clear();
		name.clear();
		email.clear();
		phone.clear();
		address.clear();
		date_start.getEditor().clear();
		dateofbirth.getEditor().clear();
		user.clear();
		pass.clear();
	}
//    -------------------------------------------------------------------------------------------------------

	@FXML
	void Add(ActionEvent event) throws Exception {
		conn = connectDB.ConnectDb();
		String sql = "insert into employee (emp_name,emp_email,emp_phone,emp_address,emp_birthday,emp_start_date,emp_gender,title_id,emp_user,emp_pass) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		try {
			if (pass.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "Password cannot be blank!!");
				System.out.println("trong r");
			} else {
				String input_text = pass.getText();
				String enBase64 = encode(input_text);
				String key = "65 12 12 12 12 12 12 12 12 12 12 12 12 12 12 11";
				String encryptedString = null;
				encryptedString = encryptorAES.encrypt(enBase64, key);

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
				pst.setString(10, encryptedString);
				pst.execute();
//				--------------------------------------------------
				// Your gmail address
				String myAccountEmail = "crmgroupapp@gmail.com";
				// Your gmail password
				String password = "crmapp0123123";

				Properties properties = new Properties();

				properties.put("mail.smtp.auth", "true");
				properties.put("mail.smtp.starttls.enable", "true");
				properties.put("mail.smtp.host", "smtp.gmail.com");
				properties.put("mail.smtp.port", "587");

				Session session = Session.getInstance(properties, new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(myAccountEmail, password);
					}
				});

				try {
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(myAccountEmail));
					message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getText()));
					message.setSubject("Account: ");
					String htmlCode = "<h2> username:<i> <u>" + user.getText()
							+ "</u> </i> </h2> <br/> <h2><b>Password:<i> <u>" + pass.getText() + "</u> </i> </b></h2>";
					message.setContent(htmlCode, "text/html");
					Transport.send(message);
					JOptionPane.showMessageDialog(null, "Add  And Send Successfully");
					System.out.println("Message sent successfully");
				} catch (Exception ex) {
					// TODO: handle exception
					ex.printStackTrace();
				}
//				------------------------------------------------
//				UpdateTable1();
				search_account();
				reset();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	@FXML
	void Delete(ActionEvent event) {
		conn = connectDB.ConnectDb();
		String sql = "delete from employee where emp_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, id.getText());
			pst.execute();
			JOptionPane.showMessageDialog(null, "Delete");
//			UpdateTable1();
			search_account();
			reset();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	void Reset(ActionEvent event) {
//		UpdateTable1();
		search_account();
		reset();
	}

	@FXML
	void Update(ActionEvent event) {
		try {
			if (id.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "Please select the data you want to delete!");
			} else {
				conn = connectDB.ConnectDb();
				String value1 = id.getText();
				String value2 = name.getText();
				String value3 = email.getText();
				String value4 = phone.getText();
				String value5 = address.getText();
				String value6 = gender.getValue();
				Integer value7 = title_id;
				String value8 = user.getText();
				String sql = "update employee set emp_name= '" + value2 + "',emp_email= '" + value3 + "',emp_phone= '"
						+ value4 + "',emp_address= '" + value5 + "',emp_gender= '" + value6 + "',title_id='" + value7
						+ "',emp_user= '" + value8 + "' where emp_id= '" + value1 + "' ";
				pst = conn.prepareStatement(sql);
				pst.execute();
				JOptionPane.showMessageDialog(null, "Update");
//				UpdateTable1();
				search_account();
				reset();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	void box_gender(ActionEvent event) {

	}

	private static Integer title_id;

	@FXML
	void box_titlle(ActionEvent event) {

		try {
			Connection con = connectDB.ConnectDb();
			String title_name = title.getValue();
			String sql = "select * from title where title_name='" + title_name + "' ";
			PreparedStatement statement;
			statement = con.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			if (set.next()) {
				title_id = set.getInt("title_id");
				System.out.println(title_id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	ObservableList<String> list = FXCollections.observableArrayList("Male", "Female");

	private List<String> getDataTitle() {

		// Define the data you will be returning, in this case, a List of Strings for
		// the ComboBox
		List<String> options = new ArrayList<>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = connectDB.ConnectDb();
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

	/*--------------------------------------------------------------------------*/
	public static String encode(String str) {
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] encoded = encoder.encode(str.getBytes());
		return new String(encoded);

	}

	public static String decode(String str) {
		Base64.Decoder decode = Base64.getDecoder();
		byte[] decoded = decode.decode(str);
		return new String(decoded);

	}

	/*--------------------------------------------------------------------------*/
	@FXML
	void getSelected(MouseEvent event) {
		index = table_account.getSelectionModel().getSelectedIndex();
		if (index <= -1) {

			return;
		}
		id.setText(col_id.getCellData(index).toString());
		name.setText(col_name.getCellData(index).toString());
		email.setText(col_email.getCellData(index).toString());
		phone.setText(col_phone.getCellData(index).toString());
		address.setText(col_address.getCellData(index).toString());
		gender.setValue(col_gender.getCellData(index).toString());
		title.setValue(col_title.getCellData(index).toString());
		user.setText(col_user.getCellData(index).toString());
//        pass.setText(col_pass.getCellData(index).toString());
	}

	public void UpdateTable1() {

//		col_id.setCellValueFactory(new PropertyValueFactory<Account1, Integer>("emp_id"));
//		col_name.setCellValueFactory(new PropertyValueFactory<Account1, String>("emp_name"));
//		col_email.setCellValueFactory(new PropertyValueFactory<Account1, String>("emp_email"));
//		col_phone.setCellValueFactory(new PropertyValueFactory<Account1, String>("emp_phone"));
//		col_address.setCellValueFactory(new PropertyValueFactory<Account1, String>("emp_address"));
//		col_gender.setCellValueFactory(new PropertyValueFactory<Account1, String>("emp_gender"));
//		col_title.setCellValueFactory(new PropertyValueFactory<Account1, String>("title_name"));
//		col_user.setCellValueFactory(new PropertyValueFactory<Account1, String>("emp_user"));
//		col_pass.setCellValueFactory(new PropertyValueFactory<Account1, String>("emp_pass"));
//		col_status.setCellValueFactory(new PropertyValueFactory<Account1, Integer>("emp_status"));
//		listM = connectDB.getDataAccount1();
//		table_account.setItems(listM);
	}

	@FXML
	void search_account() {
		col_no.setCellValueFactory(new PropertyValueFactory<Account1, Integer>("no"));
		col_id.setCellValueFactory(new PropertyValueFactory<Account1, Integer>("emp_id"));
		col_name.setCellValueFactory(new PropertyValueFactory<Account1, String>("emp_name"));
		col_email.setCellValueFactory(new PropertyValueFactory<Account1, String>("emp_email"));
		col_phone.setCellValueFactory(new PropertyValueFactory<Account1, String>("emp_phone"));
		col_address.setCellValueFactory(new PropertyValueFactory<Account1, String>("emp_address"));
		col_gender.setCellValueFactory(new PropertyValueFactory<Account1, String>("emp_gender"));
		col_title.setCellValueFactory(new PropertyValueFactory<Account1, String>("title_name"));
		col_user.setCellValueFactory(new PropertyValueFactory<Account1, String>("emp_user"));
		col_pass.setCellValueFactory(new PropertyValueFactory<Account1, String>("emp_pass"));
		col_status.setCellValueFactory(new PropertyValueFactory<Account1, Integer>("emp_status"));

		dataList = connectDB.getDataAccount1();
		table_account.setItems(dataList);
		FilteredList<Account1> filteredData = new FilteredList<>(dataList, b -> true);
		txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (person.getEmp_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (person.getEmp_email().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (person.getEmp_phone().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(person.getEmp_id()).indexOf(lowerCaseFilter) != -1)
					return true;
				else
					return false;
			});
		});
		SortedList<Account1> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(table_account.comparatorProperty());
		table_account.setItems(sortedData);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		title.setItems(FXCollections.observableArrayList(getDataTitle()));
		gender.setItems(list);
//		UpdateTable1();
		search_account();
	}
}
