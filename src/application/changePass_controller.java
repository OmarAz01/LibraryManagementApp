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
import javafx.scene.control.TextField;

public class changePass_controller implements Initializable {

	@FXML
	private Button changePass_btn;

	@FXML
	private TextField newPass_tf;

	@FXML
	private TextField oldPass_tf;

	@FXML
	private TextField tf_username;

	@FXML
	private Button back_btn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		changePass_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

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
