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
import models.Records;

public class admin_currReserved_controller implements Initializable {

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
	private TableColumn<Records, Integer> material_ID_COL;

	@FXML
	private TableColumn<Records, Integer> material_ISBN_COL;

	@FXML
	private TableColumn<Records, String> material_author_COL;

	@FXML
	private TableColumn<Records, String> material_title_COL;

	@FXML
	private TableColumn<Records, String> reserved_date_COL;

	@FXML
	private TableView<Records> tableview;

	@FXML
	private TextField tf_search;

	@FXML
	private Button button_accounts;

	@FXML
	private TableColumn<Records, String> type_COL;

	@FXML
	private TableColumn<Records, Integer> userID_COL;

	private ObservableList<Records> recordsList;
	public static TableView<Records> table;
	public static Records records;
	public static DBConnection dbConn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		dbConn = new DBConnection();
		initializeReservedTable();

		button_nav_returns.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/adminAcc/admin_checkReturns.fxml", "Check Returns");

			}

		});

		button_logout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/application/application.fxml", "LMA");

			}

		});

		button_nav_search.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/adminAcc/admin_HP.fxml", "LMA");

			}

		});

		button_nav_add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/adminAcc/admin_addRemove_material.fxml", "LMA");
			}

		});

		button_nav_report.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/adminAcc/admin_report.fxml", "Generate a Report");

			}

		});

		button_accounts.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/adminAcc/admin_accounts.fxml", "Accounts");

			}

		});

	}

	private void initializeReservedTable() {
		recordsList = FXCollections.observableArrayList();
		Connection dbConnect = dbConn.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null; 
		
		try {
			
			ps = dbConnect.prepareStatement("SELECT * FROM records WHERE issued_date IS NULL AND returned_date IS NULL");
			rs = ps.executeQuery();
			while (rs.next()) {

				recordsList.add(new Records(rs.getString("ref_num"), rs.getInt("user_id"), rs.getString("acc_type"), rs.getInt("material_id"), rs.getString("material_type"), rs.getString("material_title"), rs.getString("material_author"),
						rs.getString("ISBN"), rs.getString("reserved_date"), rs.getString("issued_date"), rs.getString("due_date"), rs.getString("returned_date")));
			}

		} catch (SQLException e) {
			System.out.println("Error");
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
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

		type_COL.setCellValueFactory(new PropertyValueFactory<>("material_type"));
		material_ID_COL.setCellValueFactory(new PropertyValueFactory<>("material_id"));
		material_title_COL.setCellValueFactory(new PropertyValueFactory<>("material_title"));
		material_author_COL.setCellValueFactory(new PropertyValueFactory<>("material_author"));
		material_ISBN_COL.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		userID_COL.setCellValueFactory(new PropertyValueFactory<>("user_id"));
		reserved_date_COL.setCellValueFactory(new PropertyValueFactory<>("reserved_date"));

		tableview.setItems(null);
		tableview.setItems(recordsList);

		// Create FiliteredList to filter data with search field
		FilteredList<Records> filteredList = new FilteredList<>(recordsList, b -> true);

		tf_search.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredList.setPredicate(material -> {

				if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {

					return true;
				}

				String searchText = newValue.toLowerCase();

				if (material.getMaterial_title().toLowerCase().indexOf(searchText) > -1) {

					tableview.refresh();
					return true;
				} else if (String.valueOf(material.getUser_id()).indexOf(searchText) > -1) {

					tableview.refresh();
					return true;

				} else {

					tableview.refresh();
					return false;

				}

			});

		});

		SortedList<Records> sortedMaterial = new SortedList<>(filteredList);

		sortedMaterial.comparatorProperty().bind(tableview.comparatorProperty());

		tableview.setItems(sortedMaterial);

		tableview.setOnMouseClicked(mouseEvent -> {

			if (mouseEvent.getClickCount() == 2) {

				records = tableview.getSelectionModel().getSelectedItem();
				Parent root = null;

				if (records != null) {

					Stage main_stage = new Stage();
					main_stage.setResizable(false);
					main_stage.initModality(Modality.APPLICATION_MODAL);
					main_stage.setTitle("Issue Material");

					try {

						FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/adminAcc/admin_issue_material.fxml")));
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
