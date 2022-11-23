package librarianAcc;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import application.DB;
import application.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Accounts;

public class librarians_report_controller implements Initializable {

	@FXML
	private TableView<Accounts> tableview;

	@FXML
	private TextField tf_search;

	@FXML
	private TableColumn<Accounts, String> userName_COL;

	@FXML
	private TableColumn<Accounts, String> lName_COL;

	@FXML
	private Button button_logout;

	@FXML
	private Button button_nav_add;

	@FXML
	private Button button_nav_report;

	@FXML
	private Button button_nav_reserved;

	@FXML
	private Button button_nav_returns;

	@FXML
	private Button button_nav_search;

	@FXML
	private TableColumn<Accounts, String> email_COL;

	@FXML
	private TableColumn<Accounts, String> fName_COL;

	@FXML
	private TableColumn<Accounts, Integer> accID_COL;

	private DBConnection dbConn;
	private ObservableList<Accounts> accountsList;
	public static TableView<Accounts> accounts;
	public static Accounts account;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.dbConn = new DBConnection();
		initializeAccountsTable();

		button_nav_returns.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/librarianAcc/librarians_checkReturns.fxml", "Check Returns");

			}

		});

		button_nav_report.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/librarianAcc/librarians_report.fxml", "Generate a Report");

			}

		});

		button_logout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/application/application.fxml", "LMA");

			}

		});

		button_nav_reserved.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/librarianAcc/librarians_currReserved.fxml", "LMA");
			}

		});

		button_nav_add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/librarianAcc/librarians_addRemove_material.fxml", "LMA");
			}

		});

		button_nav_search.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/librarianAcc/librarians_HP.fxml", "LMA");

			}

		});

	}

	private void initializeAccountsTable() {
		accountsList = FXCollections.observableArrayList();
		Connection dbConnect = dbConn.getDBConnection();
		PreparedStatement psGetAcc = null;
		ResultSet rsAccs = null;
		
		try {

			psGetAcc = dbConnect.prepareStatement("SELECT * FROM accounts WHERE account_type = 'Member';");
			rsAccs = psGetAcc.executeQuery();

			while (rsAccs.next()) {

				accountsList.add(new Accounts(rsAccs.getInt("user_id"), rsAccs.getString("account_type"), rsAccs.getString("f_name"), rsAccs.getString("l_name"), rsAccs.getString("username"), rsAccs.getString("email")));

			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (rsAccs != null) {
				try {
					rsAccs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (psGetAcc != null) {
				try {
					psGetAcc.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (dbConnect != null) {
				try {
					dbConnect.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		fName_COL.setCellValueFactory(new PropertyValueFactory<>("f_name"));
		lName_COL.setCellValueFactory(new PropertyValueFactory<>("l_name"));
		userName_COL.setCellValueFactory(new PropertyValueFactory<>("username"));
		accID_COL.setCellValueFactory(new PropertyValueFactory<>("user_id"));
		email_COL.setCellValueFactory(new PropertyValueFactory<>("email"));

		tableview.setItems(null);
		tableview.setItems(accountsList);

		FilteredList<Accounts> filteredList = new FilteredList<>(accountsList, b -> true);

		tf_search.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredList.setPredicate(account -> {

				if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {

					return true;
				}

				String searchText = newValue.toLowerCase();

				if (account.getF_name().toLowerCase().indexOf(searchText) > -1) {

					tableview.refresh();
					return true;
				} else if (account.getL_name().toLowerCase().indexOf(searchText) > -1) {

					tableview.refresh();
					return true;

				} else if (account.getUsername().toLowerCase().indexOf(searchText) > -1) {

					tableview.refresh();
					return true;

				} else if (String.valueOf(account.getUser_id()).indexOf(searchText) > -1) {

					tableview.refresh();
					return true;

				} else {

					tableview.refresh();
					return false;

				}

			});

		});

		SortedList<Accounts> sortedMaterial = new SortedList<>(filteredList);

		sortedMaterial.comparatorProperty().bind(tableview.comparatorProperty());

		tableview.setItems(sortedMaterial);

		tableview.setOnMouseClicked(mouseEvent -> {

			if (mouseEvent.getClickCount() == 2) {

				account = tableview.getSelectionModel().getSelectedItem();
				Parent root = null;

				if (account != null) {

					Stage main_stage = new Stage();
					main_stage.setResizable(false);
					main_stage.initModality(Modality.APPLICATION_MODAL);
					main_stage.setTitle("Confirm Account");

					try {

						FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/librarianAcc/librarians_userReport.fxml")));
						root = loader.load();

					} catch (IOException e) {
						e.printStackTrace();
					}

					Scene scene = new Scene(root);

					main_stage.setScene(scene);
					main_stage.show();
				}

			}

		});
	}

}
