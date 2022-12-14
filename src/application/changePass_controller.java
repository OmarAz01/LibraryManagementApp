package application;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class changePass_controller implements Initializable {

	@FXML
	private Button changePass_btn;

	@FXML
	private PasswordField newPass_tf;

	@FXML
	private PasswordField oldPass_tf;

	@FXML
	private TextField tf_username;

	@FXML
	private Button back_btn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		changePass_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				if (!newPass_tf.getText().trim().isEmpty() && !tf_username.getText().trim().isEmpty() && !oldPass_tf.getText().trim().isEmpty()) {
					
					if (newPass_tf.getText().length() > 6 && newPass_tf.getText().length() < 16) {

						try {
							if (DB.changePass(tf_username.getText(), oldPass_tf.getText(), newPass_tf.getText())) {
		
								Alert alert = new Alert(Alert.AlertType.INFORMATION);
								alert.setContentText("Successfully Changed Password");
								alert.show();
		
								DB.changeScene(event, "/application/application.fxml", "LMA");
		
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
					alert.setContentText("Input All Fields");
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
