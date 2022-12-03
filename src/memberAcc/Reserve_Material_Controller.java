package memberAcc;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.DB;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.Records;

public class Reserve_Material_Controller implements Initializable {

	private DB db;

	@FXML
	private Label author_lbl;

	@FXML
	private Label copies_lbl;

	@FXML
	private Label genre_lbl;

	@FXML
	private Button reserve_btn;

	@FXML
	private Label title_lbl;

	@FXML
	private Label type_lbl;

	@FXML
	private Label isbn_lbl;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		reserve_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				confirmReserve(event);

			}

		});

		try {

			this.db = new DB();

			author_lbl.setText(members_HP_controller.material.getAuthor());
			copies_lbl.setText(String.valueOf(members_HP_controller.material.getCopies_avail()));
			genre_lbl.setText(members_HP_controller.material.getGenre());
			title_lbl.setText(members_HP_controller.material.getTitle());
			type_lbl.setText(members_HP_controller.material.getMaterial_type());
			isbn_lbl.setText(members_HP_controller.material.getIsbn());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void confirmReserve(ActionEvent event) {

		Records materialRecord = new Records(members_HP_controller.material, db.generateRefNum(),
				Main.currUser.getUser_id());

		if (db.checkIfReserved(materialRecord.getMaterial(), Main.currUser.getUser_id())) {

			if (db.checkIfReturned(materialRecord.getMaterial(), Main.currUser.getUser_id())) {

				if (members_HP_controller.material.getCopies_avail() >= 1) {

					Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
					alert2.setContentText("Reservation has been confirmed");
					alert2.show();

					materialRecord.setMaterial_type(members_HP_controller.material.getMaterial_type());
					materialRecord.setAcc_type(Main.currUser.getAccount_type());
					materialRecord.setTitle(members_HP_controller.material.getTitle());
					materialRecord.setIsbn(members_HP_controller.material.getIsbn());
					materialRecord.setAuthor(members_HP_controller.material.getAuthor());
					materialRecord.setMaterial_id(members_HP_controller.material.getMaterial_id());
					materialRecord.setReserved_date(LocalDate.now().toString());

					db.reserveMaterial(materialRecord);

					this.copies_lbl.setText(String.valueOf(members_HP_controller.material.getCopies_avail()));

				}

				else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("No more copies available. Please check back later.");
					alert.show();
				}

			}

			else {

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("You have already reserved this item");
				alert.show();

			}

		}

		else if (members_HP_controller.material.getCopies_avail() < 1) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("No more copies available. Please check back later.");
			alert.show();

		}

		else {

			Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
			alert2.setContentText("Reservation has been confirmed");
			alert2.show();

			materialRecord.setMaterial_type(members_HP_controller.material.getMaterial_type());
			materialRecord.setAcc_type(Main.currUser.getAccount_type());
			materialRecord.setTitle(members_HP_controller.material.getTitle());
			materialRecord.setAuthor(members_HP_controller.material.getAuthor());
			materialRecord.setIsbn(members_HP_controller.material.getIsbn());
			materialRecord.setMaterial_id(members_HP_controller.material.getMaterial_id());
			materialRecord.setReserved_date(LocalDate.now().toString());

			db.reserveMaterial(materialRecord);

			this.copies_lbl.setText(String.valueOf(members_HP_controller.material.getCopies_avail()));
		}

	}

}
