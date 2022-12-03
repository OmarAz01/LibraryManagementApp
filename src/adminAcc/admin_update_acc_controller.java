package adminAcc;

import java.net.URL;
import java.util.ResourceBundle;

import application.DB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Accounts;

public class admin_update_acc_controller implements Initializable {

	@FXML
	private TextField tf_email;

	@FXML
	private TextField tf_fName;

	@FXML
	private TextField tf_lName;

	@FXML
	private TextField tf_username;

	@FXML
	private Button update_btn;
	
	@FXML
	private TextField tf_password;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		tf_email.setText(admin_accounts_controller.account.getEmail());
		tf_fName.setText(admin_accounts_controller.account.getF_name());
		tf_lName.setText(admin_accounts_controller.account.getL_name());
		tf_username.setText(admin_accounts_controller.account.getUsername());

		update_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (tf_password.getText().trim().isEmpty() && !tf_email.getText().trim().isEmpty() && !tf_fName.getText().trim().isEmpty() && !tf_lName.getText().trim().isEmpty() && !tf_username.getText().trim().isEmpty()) {
					Accounts account = new Accounts(admin_accounts_controller.account.getUser_id(), tf_username.getText(), tf_fName.getText(), tf_lName.getText(), tf_email.getText());
					String password = tf_password.getText();
					if (DB.updateAccount(account, password)) {

						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setContentText("Item Successfully Updated!");
						alert.show();

					}

					else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("Something Went Wrong. Please Try Again.");
						alert.show();

					}

				} else if (!tf_password.getText().trim().isEmpty() && !tf_email.getText().trim().isEmpty() && !tf_fName.getText().trim().isEmpty() && !tf_lName.getText().trim().isEmpty() && !tf_username.getText().trim().isEmpty()) {
					String password = tf_password.getText();
					Accounts account = new Accounts(admin_accounts_controller.account.getUser_id(), tf_username.getText(), tf_fName.getText(), tf_lName.getText(), tf_email.getText());
					
					if (DB.updateAccount(account, password)) {

						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setContentText("Item Successfully Updated!");
						alert.show();

					}

					else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("Something Went Wrong. Please Try Again.");
						alert.show();

					}
				}
				
				else {

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText("Input All Fields");
					alert.show();

				}

			}
		});

	}

}
