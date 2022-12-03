package librarianAcc;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.DBConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class librarians_issue_material_controller implements Initializable {

	@FXML
	private Label ISBN_lbl;

	@FXML
	private Label author_lbl;

	@FXML
	private Label dueDate_lbl;

	@FXML
	private Label issueDate_lbl;

	@FXML
	private Label materialID_lbl;

	@FXML
	private Button issue_btn;

	@FXML
	private Label title_lbl;

	@FXML
	private Label userID_lbl;
	
	@FXML
	private Button remove_btn;

	private DBConnection dbConn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		dbConn = new DBConnection();

		ISBN_lbl.setText(String.valueOf(librarians_currReserved_controller.records.getIsbn()));
		author_lbl.setText(librarians_currReserved_controller.records.getAuthor());
		dueDate_lbl.setText(LocalDate.now().plusDays(30).toString());
		title_lbl.setText(librarians_currReserved_controller.records.getMaterial_title());
		userID_lbl.setText(String.valueOf(librarians_currReserved_controller.records.getUser_id()));
		issueDate_lbl.setText(LocalDate.now().toString());
		materialID_lbl.setText(String.valueOf(librarians_currReserved_controller.records.getMaterial_id()));

		issue_btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				issueMaterial(event);

			}

		});
		
		remove_btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				removeReserve(event);
			}
		});

	}

	private void issueMaterial(ActionEvent event) {

		Connection dbConnect = dbConn.getDBConnection();
		PreparedStatement psCheckRecord = null;
		ResultSet rsRecord = null;
		PreparedStatement psUpdateRecord = null;
		
		try {
			psCheckRecord = dbConnect.prepareStatement("SELECT * FROM records WHERE ref_num = '" + librarians_currReserved_controller.records.getRef_num() + "';");
			rsRecord = psCheckRecord.executeQuery();
			if (rsRecord.next()) {
				try {
					psUpdateRecord = dbConnect
							.prepareStatement("UPDATE records SET issued_date = '" + LocalDate.now().toString() + "', due_date = '" + LocalDate.now().plusDays(30) + "' WHERE ref_num = '" + librarians_currReserved_controller.records.getRef_num() + "';");
					psUpdateRecord.executeUpdate();

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText("Successfully Issued Item");
					alert.show();
				} catch (SQLException e) {
					e.printStackTrace();

					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Something Went Wrong. Please Try Again");
					alert.show();
				}
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("This Reserve Has Been Removed");
				alert.show();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 finally {
			 if (rsRecord != null) {
				 try {
					rsRecord.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 if (psCheckRecord != null) {
				 try {
					psCheckRecord.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			if (psUpdateRecord != null) {
				try {
					psUpdateRecord.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (dbConnect != null) {
				try {
					dbConnect.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
	private void removeReserve(ActionEvent event) {
		Connection dbConnect = dbConn.getDBConnection();
		PreparedStatement psUpdateCopies = null;
		PreparedStatement psCheckRecord = null;
		PreparedStatement psRemoveReserve = null;
		ResultSet rsRecord = null;
		
		try {
			psCheckRecord = dbConnect.prepareStatement("SELECT * FROM records WHERE ref_num = '" + librarians_currReserved_controller.records.getRef_num() + "';");
			rsRecord = psCheckRecord.executeQuery();
			if (rsRecord.next()) {
				try {
					psUpdateCopies = dbConnect.prepareStatement("UPDATE material SET copies_avail = copies_avail + 1 WHERE material_id = '" + librarians_currReserved_controller.records.getMaterial_id() + "'");
					psRemoveReserve = dbConnect.prepareStatement("DELETE FROM records WHERE ref_num = '" + librarians_currReserved_controller.records.getRef_num() + "';");
					psRemoveReserve.executeUpdate();
					psUpdateCopies.executeUpdate();
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText("Successfully Removed Reserve");
					alert.show();
					
				} catch (SQLException e) {
					e.printStackTrace();
					
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Something Went Wrong. Please Try Again");
					alert.show();
				}
				
			}
			
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("This Reserve Has Already Been Removed");
				alert.show();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rsRecord != null) {
				try {
					rsRecord.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (psCheckRecord != null) {
				try {
					psCheckRecord.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (psRemoveReserve != null) {
				try {
					psRemoveReserve.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (psUpdateCopies != null) {
				try {
					psUpdateCopies.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (dbConnect != null) {
				try {
					dbConnect.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		}
		
		
		
	}

}
