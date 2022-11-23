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
import models.Records;

public class librarians_checkReturns_controller implements Initializable {

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
	private TableColumn<Records, String> dueDate_COL;

	@FXML
	private TableColumn<Records, Integer> material_ID_COL;

	@FXML
	private TableColumn<Records, Integer> material_ISBN_COL;

	@FXML
	private TableColumn<Records, String> material_author_COL;

	@FXML
	private TableColumn<Records, String> material_title_COL;

	@FXML
	private TableView<Records> tableview;

	@FXML
	private TextField tf_search;

	@FXML
	private TableColumn<Records, String> type_COL;

	@FXML
	private TableColumn<Records, Integer> userID_COL;

	private ObservableList<Records> recordsList;
	public static TableView<Records> table;
	public static Records records;
	private DBConnection dbConn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		dbConn = new DBConnection();

		initializeReturnsTable();

		button_nav_search.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/librarianAcc/librarians_HP.fxml", "LMA");

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

		button_nav_report.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/librarianAcc/librarians_report.fxml", "Generate a Report");

			}

		});

	}

	private void initializeReturnsTable() {

		recordsList = FXCollections.observableArrayList();
		Connection dbConnect = dbConn.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = dbConnect.prepareStatement("SELECT * FROM records WHERE returned_date = 'Requested'");
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
		dueDate_COL.setCellValueFactory(new PropertyValueFactory<>("due_date"));
		material_ISBN_COL.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		userID_COL.setCellValueFactory(new PropertyValueFactory<>("user_id"));

		tableview.setItems(null);
		tableview.setItems(recordsList);

		// Create FiliteredList to filter data with search field
		FilteredList<Records> filteredList = new FilteredList<>(recordsList, b -> true);

		tf_search.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredList.setPredicate(records -> {

				if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {

					return true;
				}

				String searchText = newValue.toLowerCase();

				if (records.getMaterial_title().toLowerCase().indexOf(searchText) > -1) {

					tableview.refresh();
					return true;
				} else if (String.valueOf(records.getUser_id()).indexOf(searchText) > -1) {

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
					main_stage.setTitle("Confirm Return");

					try {

						FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/librarianAcc/librarians_return_material.fxml")));
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
