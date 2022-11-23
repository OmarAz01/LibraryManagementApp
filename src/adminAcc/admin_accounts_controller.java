package adminAcc;

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

public class admin_accounts_controller implements Initializable {

	@FXML
	private TableColumn<Accounts, String> email_COL;

	@FXML
	private TableColumn<Accounts, String> fName_COL;

	@FXML
	private TableColumn<Accounts, String> lName_COL;

	@FXML
	private TableView<Accounts> tableview;

	@FXML
	private TableColumn<Accounts, String> type_COL;

	@FXML
	private TableColumn<Accounts, Integer> userID_COL;

	@FXML
	private TableColumn<Accounts, String> userName_COL;

	@FXML
	private Button button_accounts;

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
	private Button add_btn;

	@FXML
	private TextField tf_search;

	private ObservableList<Accounts> accountsList;
	public static Accounts account;
	private DBConnection dbConn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		dbConn = new DBConnection();
		initializeAccountsTable();

		button_nav_returns.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/adminAcc/admin_checkReturns.fxml", "Check Returns");

			}

		});

		button_nav_report.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/adminAcc/admin_report.fxml", "Generate a Report");

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

				DB.changeScene(event, "/adminAcc/admin_currReserved.fxml", "LMA");
			}

		});

		button_nav_add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/adminAcc/admin_addRemove_material.fxml", "LMA");
			}

		});

		button_nav_search.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/adminAcc/admin_HP.fxml", "LMA");

			}

		});

		button_accounts.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/adminAcc/admin_accounts.fxml", "Accounts");

			}

		});

		add_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Stage main_stage = new Stage();
				main_stage.setResizable(false);
				main_stage.initModality(Modality.APPLICATION_MODAL);
				main_stage.setTitle("Add Account");
				Parent root = null;

				try {

					FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/adminAcc/admin_add_acc.fxml")));
					root = loader.load();

				} catch (IOException e) {
					e.printStackTrace();
				}

				Scene scene = new Scene(root);

				main_stage.setScene(scene);
				main_stage.show();

			}

		});

	}

	private void initializeAccountsTable() {
		accountsList = FXCollections.observableArrayList();
		Connection dbConnect = dbConn.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = dbConnect.prepareStatement("SELECT * FROM accounts");
			rs = ps.executeQuery();

			while (rs.next()) {

				accountsList.add(new Accounts(rs.getInt("user_id"), rs.getString("account_type"), rs.getString("f_name"), rs.getString("l_name"), rs.getString("username"), rs.getString("email")));
			}

		} catch (SQLException e) {
			System.out.println("Error");
			e.printStackTrace();
		}

		type_COL.setCellValueFactory(new PropertyValueFactory<>("account_type"));
		userName_COL.setCellValueFactory(new PropertyValueFactory<>("username"));
		userID_COL.setCellValueFactory(new PropertyValueFactory<>("user_id"));
		email_COL.setCellValueFactory(new PropertyValueFactory<>("email"));
		fName_COL.setCellValueFactory(new PropertyValueFactory<>("f_name"));
		lName_COL.setCellValueFactory(new PropertyValueFactory<>("l_name"));

		tableview.setItems(null);
		tableview.setItems(accountsList);

		// Create FiliteredList to filter data with search field
		FilteredList<Accounts> filteredList = new FilteredList<>(accountsList, b -> true);

		tf_search.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredList.setPredicate(account -> {

				if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {

					return true;
				}

				String searchText = newValue.toLowerCase();

				if (String.valueOf(account.getUser_id()).indexOf(searchText) > -1) {

					tableview.refresh();
					return true;
				} else if (account.getUsername().toLowerCase().indexOf(searchText) > -1) {

					tableview.refresh();
					return true;

				} else if (account.getF_name().toLowerCase().indexOf(searchText) > -1) {

					tableview.refresh();
					return true;

				} else if (account.getL_name().toLowerCase().indexOf(searchText) > -1) {

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
					main_stage.setTitle("");

					try {

						FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/adminAcc/admin_AccChoice.fxml")));
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
