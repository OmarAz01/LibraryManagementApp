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
import models.Material;

public class admin_add_material_controller implements Initializable {

	@FXML
	private Button add_btn;

	@FXML
	private TextField tf_ISBN;

	@FXML
	private TextField tf_Type;

	@FXML
	private TextField tf_author;

	@FXML
	private TextField tf_copies;

	@FXML
	private TextField tf_genre;

	@FXML
	private TextField tf_title;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		add_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				String isbn;
				if (tf_ISBN.getText().trim().isEmpty()) {
					isbn = "";
				}
				else {
					isbn = tf_ISBN.getText();
				}

				if (!tf_Type.getText().trim().isEmpty() && !tf_author.getText().trim().isEmpty() && !tf_genre.getText().trim().isEmpty() && !tf_title.getText().trim().isEmpty()) {

					if (Integer.valueOf(tf_copies.getText()) > 0) {

						Material material = new Material(tf_title.getText(), tf_author.getText(), tf_Type.getText(), tf_genre.getText(), isbn, Integer.valueOf(tf_copies.getText()));

						if (DB.addMaterial(material)) {

							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setContentText("Item Successfully Added!");
							alert.show();

						}

						else {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setContentText("An error ocurred please try again later");
							alert.show();

						}

					}

					else {

						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("Copies must be greater than 0");
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
