package librarianAcc;

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

public class librarians_update_material_controller implements Initializable {
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

	@FXML
	private Button update_btn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		tf_author.setText(librarians_addRemove_material_controller.material.getAuthor());
		tf_copies.setText(String.valueOf(librarians_addRemove_material_controller.material.getCopies_tot()));
		tf_genre.setText(librarians_addRemove_material_controller.material.getGenre());
		tf_ISBN.setText(String.valueOf(librarians_addRemove_material_controller.material.getIsbn()));
		tf_title.setText(librarians_addRemove_material_controller.material.getTitle());
		tf_Type.setText(librarians_addRemove_material_controller.material.getMaterial_type());

		update_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (!tf_author.getText().trim().isEmpty() && !tf_ISBN.getText().trim().isEmpty() && !tf_Type.getText().trim().isEmpty() && !tf_copies.getText().trim().isEmpty() && !tf_genre.getText().trim().isEmpty()
						&& !tf_title.getText().trim().isEmpty()) {

					Material material = new Material(librarians_addRemove_material_controller.material.getMaterial_id(), tf_title.getText(), tf_author.getText(), tf_Type.getText(), tf_genre.getText(), tf_ISBN.getText(),
							Integer.valueOf(tf_copies.getText()), librarians_addRemove_material_controller.material.getCopies_avail());

					if (DB.updateMaterial(material)) {

						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setContentText("Item Successfully Updated!");
						alert.show();

					}

					else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("Something Went Wrong. Please Try Again.");
						alert.show();

					}

				} else {

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText("Input All Fields");
					alert.show();

				}

			}
		});

	}

}
