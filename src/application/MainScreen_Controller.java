package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MainScreen_Controller implements Initializable {

	@FXML
	private Button button_admin;

	@FXML
	private Button button_librarian;

	@FXML
	private Button button_member;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		button_member.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/memberAcc/members_signin.fxml", "Members Sign In");

			}
		});

		button_librarian.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/librarianAcc/librarians_signin.fxml", "Librarians Sign In");

			}

		});

		button_admin.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/adminAcc/admin_signin.fxml", "Admin Sign In");

			}

		});

	}

}
