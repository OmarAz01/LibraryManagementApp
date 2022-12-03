package adminAcc;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import application.DB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class admin_add_acc_controller implements Initializable {

	@FXML
	private TextField tf_accType;

	@FXML
	private Button add_btn;

	@FXML
	private TextField pf_password;

	@FXML
	private TextField tf_email;

	@FXML
	private TextField tf_fName;

	@FXML
	private TextField tf_lName;

	@FXML
	private TextField tf_username;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		add_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (!tf_accType.getText().trim().isEmpty() && !tf_username.getText().trim().isEmpty()
						&& !tf_email.getText().trim().isEmpty() && !tf_fName.getText().trim().isEmpty()
						&& !tf_lName.getText().trim().isEmpty() && !pf_password.getText().trim().isEmpty()) {

					if (tf_accType.getText().toLowerCase().equals("member")
							|| tf_accType.getText().toLowerCase().equals("librarian")
							|| tf_accType.getText().toLowerCase().equals("admin")) {
						
						if (pf_password.getText().length() > 6 && pf_password.getText().length() < 16) {
							

							// Formats account type
							String acctype = tf_accType.getText().toLowerCase();
							acctype = acctype.substring(0, 1).toUpperCase() + acctype.substring(1);

							try {
								if (DB.addAccount(acctype, tf_fName.getText(), tf_lName.getText(), tf_username.getText(),
										pf_password.getText(), tf_email.getText())) {
	
									Alert alert = new Alert(Alert.AlertType.INFORMATION);
									alert.setContentText("Account Successfully Added!");
									alert.show();
	
								} else {
	
									Alert alert = new Alert(Alert.AlertType.ERROR);
									alert.setContentText("Something Went Wrong");
									alert.show();
	
								}
							} catch (NoSuchAlgorithmException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setContentText("Password Must Be Between 6 and 16 Characters");
							alert.show();
							
						}

					} else {

						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("Enter a Correct Account Type");
						alert.show();

					}

				} else {

					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Please fill out all fields");
					alert.show();
				}

			}

		});

	}

}
