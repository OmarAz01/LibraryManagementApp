package librarianAcc;

import java.net.URL;
import java.util.ResourceBundle;

import application.DB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class librarians_choice_controller implements Initializable {

	@FXML
	private Button remove_btn;

	@FXML
	private Button update_btn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		remove_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/librarianAcc/librarians_remove_material.fxml", "Remove Material");

			}

		});

		update_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DB.changeScene(event, "/librarianAcc/librarians_update_material.fxml", "Update Material");

			}

		});

	}

}
