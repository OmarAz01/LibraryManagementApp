package adminAcc;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.DBConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class admin_remove_material_controller implements Initializable {

	@FXML
	private Label ISBN_lbl;

	@FXML
	private Label author_lbl;

	@FXML
	private Label genre_lbl;

	@FXML
	private Label materialID_lbl;

	@FXML
	private Label materialType_lbl;

	@FXML
	private Button remove_btn;

	@FXML
	private Label title_lbl;

	@FXML
	private Label totCopies_lbl;

	private DBConnection dbConn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		dbConn = new DBConnection();

		ISBN_lbl.setText(String.valueOf(admin_addRemove_material_controller.material.getIsbn()));
		author_lbl.setText(admin_addRemove_material_controller.material.getAuthor());
		genre_lbl.setText(admin_addRemove_material_controller.material.getGenre());
		materialID_lbl.setText(String.valueOf(admin_addRemove_material_controller.material.getMaterial_id()));
		materialType_lbl.setText(admin_addRemove_material_controller.material.getMaterial_type());
		title_lbl.setText(admin_addRemove_material_controller.material.getTitle());
		totCopies_lbl.setText(String.valueOf(admin_addRemove_material_controller.material.getCopies_tot()));

		remove_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Connection dbConnect = dbConn.getDBConnection();
				PreparedStatement psRemove = null;
				
				try {
					psRemove = dbConnect.prepareStatement("DELETE FROM material WHERE material_id = '" + admin_addRemove_material_controller.material.getMaterial_id() + "';");
					psRemove.executeUpdate();

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Item Removed Successfully");
					alert.show();
				} catch (SQLException e) {
					e.printStackTrace();

					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Something Went Wrong");
					alert.show();
				} finally {
					if (psRemove != null) {
						try {
							psRemove.close();
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

		});

	}

}
