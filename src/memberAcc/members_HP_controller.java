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
import models.Material;

public class members_HP_controller implements Initializable {

	@FXML
	public Button button_logout;

	@FXML
	public Button button_nav_account;

	@FXML
	public Button button_nav_history;

	@FXML
	public Button button_nav_return;

	@FXML
	public Button button_nav_search;

	@FXML
	private TableView<Material> tableview;

	@FXML
	private TableColumn<Material, String> type_COL, material_title_COL, author_COL, genre_COL;

	@FXML
	private TableColumn<Material, Integer> material_ID_COL, copies_COL, isbn_COL;

	@FXML
	private TextField tf_search;

	private ObservableList<Material> materialList;
	public static TableView<Material> table;
	public static Material material;
	private DBConnection dbConn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		dbConn = new DBConnection();

		initializeSearchTable();

		// Event handlers that will handle buttons on the navigation pane
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

		button_nav_history.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/memberAcc/members_history.fxml", "History");

			}

		});

	}

	// Method to fill table with all material in the database
	private void initializeSearchTable() {
		
		materialList = FXCollections.observableArrayList();
		Connection dbConnect = dbConn.getDBConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {

			ps = dbConnect.prepareStatement("SELECT * FROM material");
			rs = ps.executeQuery();
			

			while (rs.next()) {

				materialList.add(new Material(rs.getInt("material_id"), rs.getString("title"), rs.getString("author"), rs.getString("material_type"), rs.getString("genre"), rs.getString("isbn"), rs.getInt("copies_tot"), rs.getInt("copies_avail")));
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
		material_title_COL.setCellValueFactory(new PropertyValueFactory<>("title"));
		author_COL.setCellValueFactory(new PropertyValueFactory<>("author"));
		genre_COL.setCellValueFactory(new PropertyValueFactory<>("genre"));
		isbn_COL.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		copies_COL.setCellValueFactory(new PropertyValueFactory<>("copies_avail"));

		tableview.setItems(null);
		tableview.setItems(materialList);

		// Create FiliteredList to filter data with search field
		FilteredList<Material> filteredList = new FilteredList<>(materialList, b -> true);

		tf_search.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredList.setPredicate(material -> {

				if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {

					return true;
				}

				String searchText = newValue.toLowerCase();

				if (material.getTitle().toLowerCase().indexOf(searchText) > -1) {

					tableview.refresh();
					return true;
				} else if (material.getAuthor().toLowerCase().indexOf(searchText) > -1) {

					tableview.refresh();
					return true;

				} else if (material.getGenre().toLowerCase().indexOf(searchText) > -1) {

					tableview.refresh();
					return true;

				} else if (material.getMaterial_type().toLowerCase().indexOf(searchText) > -1) {

					tableview.refresh();
					return true;

				} else {

					tableview.refresh();
					return false;

				}

			});

		});

		SortedList<Material> sortedMaterial = new SortedList<>(filteredList);

		sortedMaterial.comparatorProperty().bind(tableview.comparatorProperty());

		tableview.setItems(sortedMaterial);

		tableview.setOnMouseClicked(mouseEvent -> {

			if (mouseEvent.getClickCount() == 2) {

				material = tableview.getSelectionModel().getSelectedItem();
				Parent root = null;

				if (material != null && material.getMaterial_type().equals("Book")) {

					Stage main_stage = new Stage();
					main_stage.setResizable(false);
					main_stage.initModality(Modality.APPLICATION_MODAL);
					main_stage.setTitle("Confirm Reservation");

					try {

						FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("Reserve_Material.fxml")));
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
