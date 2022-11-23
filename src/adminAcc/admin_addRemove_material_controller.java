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
import models.Material;

public class admin_addRemove_material_controller implements Initializable {

	@FXML
	private TableColumn<Material, Integer> isbn_COL;

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
	private TableColumn<Material, String> genre_COL;

	@FXML
	private TableColumn<Material, String> author_COL;

	@FXML
	private TableColumn<Material, Integer> available_COL;

	@FXML
	private Button add_btn;

	@FXML
	private TableColumn<Material, Integer> material_ID_COL;

	@FXML
	private TableColumn<Material, String> material_title_COL;

	@FXML
	private TableView<Material> tableview;

	@FXML
	private TextField tf_search;

	@FXML
	private TableColumn<Material, Integer> totCopies_COL;

	@FXML
	private TableColumn<Material, String> type_COL;

	@FXML
	private Button button_accounts;

	private ObservableList<Material> materialList;
	public static Material material;
	private DBConnection dbConn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		dbConn = new DBConnection();
		initializeRemoveTable();

		button_accounts.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/adminAcc/admin_accounts.fxml", "Accounts");

			}

		});

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

		button_nav_reserved.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/adminAcc/admin_currReserved.fxml", "LMA");
			}

		});

		button_nav_search.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/adminAcc/admin_HP.fxml", "LMA");

			}

		});

		button_nav_report.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/adminAcc/admin_report.fxml", "Generate a Report");

			}

		});

		add_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Stage main_stage = new Stage();
				main_stage.setResizable(false);
				main_stage.initModality(Modality.APPLICATION_MODAL);
				main_stage.setTitle("Add Material");
				Parent root = null;

				try {

					FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/adminAcc/admin_add_material.fxml")));
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

	private void initializeRemoveTable() {
		
		materialList = FXCollections.observableArrayList();
		Connection dbConnect = dbConn.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
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
		available_COL.setCellValueFactory(new PropertyValueFactory<>("copies_avail"));
		totCopies_COL.setCellValueFactory(new PropertyValueFactory<>("copies_tot"));

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

				} else if (String.valueOf(material.getMaterial_id()).indexOf(searchText) > -1) {

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

				if (material != null) {

					Stage main_stage = new Stage();
					main_stage.setResizable(false);
					main_stage.initModality(Modality.APPLICATION_MODAL);
					main_stage.setTitle("");

					try {

						FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/adminAcc/admin_choice.fxml")));
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
