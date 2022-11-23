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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class admin_remove_acc_controller implements Initializable {

	@FXML
	private Label accType_lbl;

	@FXML
	private Label email_lbl;

	@FXML
	private Label fName_lbl;

	@FXML
	private Label lName_lbl;

	@FXML
	private Button remove_btn;

	@FXML
	private Label userID_lbl;

	@FXML
	private Label username_lbl;

	private DBConnection dbConn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		dbConn = new DBConnection();

		accType_lbl.setText(admin_accounts_controller.account.getAccount_type());
		email_lbl.setText(admin_accounts_controller.account.getEmail());
		fName_lbl.setText(admin_accounts_controller.account.getF_name());
		lName_lbl.setText(admin_accounts_controller.account.getL_name());
		userID_lbl.setText(String.valueOf(admin_accounts_controller.account.getUser_id()));
		username_lbl.setText(admin_accounts_controller.account.getUsername());

		remove_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Connection dbConnect = dbConn.getDBConnection();
				PreparedStatement psRemove = null;

				try {

					psRemove = dbConnect.prepareStatement("DELETE FROM accounts WHERE user_id = '" + admin_accounts_controller.account.getUser_id() + "';");
					psRemove.executeUpdate();

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Account Removed Successfully");
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
