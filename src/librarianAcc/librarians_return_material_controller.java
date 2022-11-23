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
import javafx.scene.control.Label;

public class librarians_return_material_controller implements Initializable {

	@FXML
	private Label ISBN_lbl;

	@FXML
	private Label author_lbl;

	@FXML
	private Label daysLate_lbl;

	@FXML
	private Label dueDate_lbl;

	@FXML
	private Label fee_lbl;

	@FXML
	private Button return_btn;

	@FXML
	private Label title_lbl;

	@FXML
	private Label userID_lbl;

	private DB db;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		return_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				confirmReturn(event);

			}

		});

		try {

			this.db = new DB();

			int daysLate = (int) (DB.getFee(librarians_checkReturns_controller.records.getRef_num()) / .17);

			ISBN_lbl.setText(String.valueOf(librarians_checkReturns_controller.records.getIsbn()));
			author_lbl.setText(librarians_checkReturns_controller.records.getAuthor());
			daysLate_lbl.setText(String.valueOf(daysLate));
			dueDate_lbl.setText(librarians_checkReturns_controller.records.getDue_date());
			fee_lbl.setText(String.valueOf(DB.getFee(librarians_checkReturns_controller.records.getRef_num())));
			title_lbl.setText(librarians_checkReturns_controller.records.getMaterial_title());
			userID_lbl.setText(String.valueOf(librarians_checkReturns_controller.records.getUser_id()));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void confirmReturn(ActionEvent event) {

		if (db.returnMaterial(librarians_checkReturns_controller.records)) {

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Item Successfully Returned");
			alert.show();

		}

		else {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("This item has already been returned");
			alert.show();

		}

	}

}
