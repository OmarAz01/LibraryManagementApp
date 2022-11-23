package memberAcc;

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
import application.Main;
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

public class members_history_controller implements Initializable {

	@FXML
	private TableColumn<Records, Integer> ISBN_COL;

	@FXML
	private TableColumn<Records, String> author_COL;

	@FXML
	private TableColumn<Records, Integer> material_ID_COL;

	@FXML
	private Button button_logout;

	@FXML
	private Button button_nav_account;

	@FXML
	private Button button_nav_history;

	@FXML
	private Button button_nav_return;

	@FXML
	private Button button_nav_search;

	@FXML
	private TableColumn<Records, String> issued_date_COL;

	@FXML
	private TableColumn<Records, String> material_title_COL;

	@FXML
	private TableColumn<Records, String> reserved_date_COL;

	@FXML
	private TableColumn<Records, String> returned_date_COL;

	@FXML
	private TableView<Records> tableview;

	@FXML
	private TextField tf_search;

	@FXML
	private TableColumn<Records, String> type_COL;

	private ObservableList<Records> recordsList;
	public static TableView<Records> table;
	public static Records records;
	private DBConnection dbConn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		dbConn = new DBConnection();
		initializeHistoryTable();

		button_logout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/application/application.fxml", "Sign In");

			}

		});

		button_nav_return.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/memberAcc/members_return.fxml", "Returns");

			}

		});

		button_nav_search.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/memberAcc/members_HP.fxml", "LMA");

			}

		});

	}

	public void initializeHistoryTable() {
		
		recordsList = FXCollections.observableArrayList();
		Connection dbConnection = dbConn.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			ps = dbConnection.prepareStatement("SELECT * FROM records WHERE user_id = '" + Main.currUser.getUser_id() + "'");
			rs = ps.executeQuery();

			while (rs.next()) {

				recordsList.add(new Records(rs.getString("ref_num"), rs.getInt("user_id"), rs.getString("acc_type"), rs.getInt("material_id"), rs.getString("material_type"), rs.getString("material_title"), rs.getString("material_author"),
						rs.getString("ISBN"), rs.getString("reserved_date"), rs.getString("issued_date"), rs.getString("due_date"), rs.getString("returned_date")));
			}

		} catch (SQLException e) {
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
			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		material_ID_COL.setCellValueFactory(new PropertyValueFactory<>("material_id"));
		ISBN_COL.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		author_COL.setCellValueFactory(new PropertyValueFactory<>("material_author"));
		issued_date_COL.setCellValueFactory(new PropertyValueFactory<>("issued_date"));
		material_title_COL.setCellValueFactory(new PropertyValueFactory<>("material_title"));
		reserved_date_COL.setCellValueFactory(new PropertyValueFactory<>("reserved_date"));
		returned_date_COL.setCellValueFactory(new PropertyValueFactory<>("returned_date"));
		type_COL.setCellValueFactory(new PropertyValueFactory<>("material_type"));

		tableview.setItems(null);
		tableview.setItems(recordsList);

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
				}

				else if (String.valueOf(records.getMaterial_id()).indexOf(searchText) > -1) {

					tableview.refresh();
					return true;

				}

				else if (records.getMaterial_type().toLowerCase().indexOf(searchText) > -1) {

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

				if (records != null && records.getIssued_date() != "Cancelled" && records.getIssued_date() == null) {

					Stage main_stage = new Stage();
					main_stage.setResizable(false);
					main_stage.initModality(Modality.APPLICATION_MODAL);
					main_stage.setTitle("Cancel Reserve");

					try {

						FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("cancel_reservation.fxml")));
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
