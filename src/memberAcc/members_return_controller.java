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

public class members_return_controller implements Initializable {

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
	private Button button_search;

	@FXML
	private TableColumn<Records, Integer> isbn_COL;

	@FXML
	private TableColumn<Records, String> author_COL;

	@FXML
	private TableColumn<Records, String> due_date_COL;

	@FXML
	private TableColumn<Records, Integer> material_id_COL;

	@FXML
	private TableView<Records> tableview;

	@FXML
	private TextField tf_search;

	@FXML
	private TableColumn<Records, String> title_COL;

	private ObservableList<Records> recordsList;
	public static TableView<Records> table;
	public static Records records;
	private DBConnection dbConn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		dbConn = new DBConnection();
		initializeReturnTable();

		// Event handlers that will handle buttons on the navigation pane
		button_nav_search.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/memberAcc/members_HP.fxml", "Search");
			}

		});

		button_logout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/application/application.fxml", "Sign In");

			}

		});

		button_nav_history.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/memberAcc/members_history.fxml", "History");

			}

		});

	}

	private void initializeReturnTable() {
		
		recordsList = FXCollections.observableArrayList();
		Connection dbConnect = dbConn.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			ps = dbConnect.prepareStatement("SELECT * FROM records WHERE user_id = '" + Main.currUser.getUser_id() + "' AND returned_date IS NULL AND issued_date IS NOT NULL;");
			rs = ps.executeQuery();

			while (rs.next()) {

				recordsList.add(new Records(rs.getString("ref_num"), rs.getInt("user_id"), rs.getString("acc_type"), rs.getInt("material_id"), rs.getString("material_type"), rs.getString("material_title"), rs.getString("material_author"),
						rs.getString("ISBN"), rs.getString("reserved_date"), rs.getString("issued_date"), rs.getString("due_date"), rs.getString("returned_date")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		isbn_COL.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		material_id_COL.setCellValueFactory(new PropertyValueFactory<>("material_id"));
		title_COL.setCellValueFactory(new PropertyValueFactory<>("material_title"));
		author_COL.setCellValueFactory(new PropertyValueFactory<>("material_author"));
		due_date_COL.setCellValueFactory(new PropertyValueFactory<>("due_date"));

		tableview.setItems(null);
		tableview.setItems(recordsList);

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

						FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("return_material.fxml")));
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
