package memberAcc;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.DB;
import application.DBConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class return_material_controller implements Initializable {

	@FXML
	private Label author_lbl;

	@FXML
	private Label due_date_lbl;

	@FXML
	private Label fee_lbl;

	@FXML
	private Label isbn_lbl;

	@FXML
	private Button return_btn;

	@FXML
	private Label title_lbl;

	private DBConnection dbConn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		dbConn = new DBConnection();

		try {

			author_lbl.setText(members_return_controller.records.getAuthor());
			due_date_lbl.setText(members_return_controller.records.getDue_date());
			fee_lbl.setText(String.valueOf(DB.getFee(members_return_controller.records.getRef_num())));
			isbn_lbl.setText(String.valueOf(members_return_controller.records.getIsbn()));
			title_lbl.setText(members_return_controller.records.getMaterial_title());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				PreparedStatement ps = null;
				Connection dbConnect = dbConn.getDBConnection();

				try {
					ps = dbConnect.prepareStatement("UPDATE records SET returned_date = 'Requested' WHERE ref_num = '" + members_return_controller.records.getRef_num() + "';");
					ps.executeUpdate();
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText("A Return Request Has Been Made. Speak To A Librarian To Make The Return");
					alert.show();
				} catch (SQLException e) {

					e.printStackTrace();
					
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Something Went Wrong");
					alert.show();
				} finally {

					if (ps != null) {
						try {
							ps.close();
						} catch (SQLException e) {
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
