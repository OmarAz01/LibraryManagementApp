package adminAcc;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import application.DB;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class admin_signin_controller implements Initializable {

	@FXML
	private Button button_login;

	@FXML
	private PasswordField tf_password;

	@FXML
	private TextField tf_username;

	@FXML
	private Button changePass_btn;

	@FXML
	private Button back_btn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		button_login.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					Main.currUser = DB.logIn(event, tf_username.getText(), tf_password.getText(), "Admin");
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		changePass_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/application/changePass.fxml", "Change Password");

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
