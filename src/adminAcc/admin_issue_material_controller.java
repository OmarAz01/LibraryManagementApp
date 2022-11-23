package adminAcc;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.DBConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class admin_issue_material_controller implements Initializable {

	@FXML
	private Label ISBN_lbl;

	@FXML
	private Label author_lbl;

	@FXML
	private Label dueDate_lbl;

	@FXML
	private Label issueDate_lbl;

	@FXML
	private Label materialID_lbl;

	@FXML
	private Button return_btn;

	@FXML
	private Label title_lbl;

	@FXML
	private Label userID_lbl;

	private DBConnection dbConn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		dbConn = new DBConnection();

		ISBN_lbl.setText(String.valueOf(admin_currReserved_controller.records.getIsbn()));
		author_lbl.setText(admin_currReserved_controller.records.getAuthor());
		dueDate_lbl.setText(LocalDate.now().plusDays(30).toString());
		title_lbl.setText(admin_currReserved_controller.records.getMaterial_title());
		userID_lbl.setText(String.valueOf(admin_currReserved_controller.records.getUser_id()));
		issueDate_lbl.setText(LocalDate.now().toString());
		materialID_lbl.setText(String.valueOf(admin_currReserved_controller.records.getMaterial_id()));

		return_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				issueMaterial(event);

			}

		});

	}

	private void issueMaterial(ActionEvent event) {

		Connection dbConnect = dbConn.getDBConnection();
		PreparedStatement ps = null;
		try {
			ps = dbConnect.prepareStatement("UPDATE records SET issued_date = '" + LocalDate.now().toString() + "', due_date = '" + LocalDate.now().plusDays(30) + "' WHERE ref_num = '" + admin_currReserved_controller.records.getRef_num() + "';");
			ps.executeUpdate();

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Successfully Issued Item");
			alert.show();
		} catch (SQLException e) {
			e.printStackTrace();
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Something Went Wrong. Please Try Again");
			alert.show();
		} finally {
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

	}

}
