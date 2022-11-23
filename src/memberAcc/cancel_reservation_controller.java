package memberAcc;

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

public class cancel_reservation_controller implements Initializable {

	@FXML
	private Label author_lbl;

	@FXML
	private Button cancel_btn;

	@FXML
	private Label isbn_lbl;

	@FXML
	private Label material_id_lbl;

	@FXML
	private Label reserved_date_lbl;

	@FXML
	private Label title_lbl;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {

			author_lbl.setText(members_history_controller.records.getAuthor());
			isbn_lbl.setText(String.valueOf(members_history_controller.records.getIsbn()));
			material_id_lbl.setText(String.valueOf(members_history_controller.records.getMaterial_id()));
			reserved_date_lbl.setText(members_history_controller.records.getReserved_date());
			title_lbl.setText(members_history_controller.records.getMaterial_title());

		} catch (Exception e) {
			e.printStackTrace();
		}

		cancel_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (DB.cancelReserve(members_history_controller.records)) {
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText("Cancellation Successfull");
					alert.show();
				}
				else {
					
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("You Have Already Cancelled This Reservation");
					alert.show();
				}
			
			}

		});

	

}
}

