package memberAcc;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController implements Initializable {

	@FXML
	private Button button_register;

	@FXML
	private TextField tf_email;

	@FXML
	private TextField tf_fname;

	@FXML
	private TextField tf_lname;

	@FXML
	private PasswordField tf_password;

	@FXML
	private TextField tf_username;

	@FXML
	private Button back_btn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		button_register.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !tf_fname.getText().trim().isEmpty() && !tf_lname.getText().trim().isEmpty() && !tf_email.getText().trim().isEmpty()) {

					try {
						DB.signUp(event, tf_fname.getText(), tf_lname.getText(), tf_username.getText(), tf_password.getText(), tf_email.getText(), "Member");
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Please fill out all fields");
					alert.show();
				}
			}
		});

		back_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/application/application.fxml", "Sign In");
			}
		});

	}

}
