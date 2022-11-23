package librarianAcc;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Records;

public class librarians_userReport_controller implements Initializable {

	@FXML
	private TableColumn<Records, String> issuedDate_COL;

	@FXML
	private TableColumn<Records, String> author_COL;

	@FXML
	private TableColumn<Records, Integer> materialID_COL;

	@FXML
	private TableColumn<Records, String> ref_num_COL;

	@FXML
	private TableColumn<Records, String> reservedDate_COL;

	@FXML
	private TableColumn<Records, String> returnedDate_COL;

	@FXML
	private TableView<Records> tableview;

	@FXML
	private TextField tf_search;

	@FXML
	private TableColumn<Records, String> title_COL;

	@FXML
	private TableColumn<Records, String> userID_COL;

	private DBConnection dbConn;
	private ObservableList<Records> recordsList;
	public static TableView<Records> table;
	public static Records records;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.dbConn = new DBConnection();

		initializeUserHistoryTable();

	}

	private void initializeUserHistoryTable() {
		recordsList = FXCollections.observableArrayList();
		Connection dbConnect = dbConn.getDBConnection();
		PreparedStatement psGetRecord = null;
		ResultSet rsRecord = null;
		try {



			psGetRecord = dbConnect.prepareStatement("SELECT * FROM records WHERE user_id = '" + librarians_report_controller.account.getUser_id() + "';");
			rsRecord = psGetRecord.executeQuery();

			while (rsRecord.next()) {

				recordsList.add(new Records(rsRecord.getString("ref_num"), rsRecord.getInt("user_id"), rsRecord.getString("acc_type"), rsRecord.getInt("material_id"), rsRecord.getString("material_type"), rsRecord.getString("material_title"),
						rsRecord.getString("material_author"), rsRecord.getString("ISBN"), rsRecord.getString("reserved_date"), rsRecord.getString("issued_date"), rsRecord.getString("due_date"), rsRecord.getString("returned_date")));

			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (rsRecord != null) {
				try {
					rsRecord.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (psGetRecord != null) {
				try {
					psGetRecord.close();
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

		issuedDate_COL.setCellValueFactory(new PropertyValueFactory<>("issued_date"));
		author_COL.setCellValueFactory(new PropertyValueFactory<>("material_author"));
		materialID_COL.setCellValueFactory(new PropertyValueFactory<>("material_id"));
		ref_num_COL.setCellValueFactory(new PropertyValueFactory<>("ref_num"));
		reservedDate_COL.setCellValueFactory(new PropertyValueFactory<>("reserved_date"));
		returnedDate_COL.setCellValueFactory(new PropertyValueFactory<>("returned_date"));
		title_COL.setCellValueFactory(new PropertyValueFactory<>("material_title"));
		userID_COL.setCellValueFactory(new PropertyValueFactory<>("user_id"));

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
				} else if (records.getAuthor().toLowerCase().indexOf(searchText) > -1) {

					tableview.refresh();
					return true;

				} else if (String.valueOf(records.getMaterial_id()).indexOf(searchText) > -1) {

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

	}

}
