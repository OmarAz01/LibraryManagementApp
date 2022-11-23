package application;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import memberAcc.members_HP_controller;
import models.Accounts;
import models.Material;
import models.Records;

public class DB {

	public static void changeScene(ActionEvent event, String fxmlFile, String title) {

		Parent root = null;

		try {

			FXMLLoader loader = new FXMLLoader(DB.class.getResource(fxmlFile));
			root = loader.load();

		} catch (IOException e) {

			e.printStackTrace();

		}

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root, 1024, 768));
		stage.setResizable(false);
		stage.show();

	}

	public static void signUp(ActionEvent event, String f_Name, String l_Name, String username, String password, String email, String accType) throws NoSuchAlgorithmException {

		Connection connection = null;
		PreparedStatement psInsert = null;
		PreparedStatement psCheckUser = null;
		ResultSet resultSet = null;

		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryappdb", "root", "Root12345");
			
			// Prevents multiple of the same username from being created by checking if username exists
			psCheckUser = connection.prepareStatement("SELECT * FROM accounts WHERE username = '" + username + "';");
			resultSet = psCheckUser.executeQuery();

			if (resultSet.isBeforeFirst()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Username already exists");
				alert.show();
			}

			else {
				String encryptedPass = md5Encrypt.encryptPass(password);

				psInsert = connection.prepareStatement("INSERT INTO accounts (account_type, f_name, l_name, username, password, email) VALUES (?, ?, ?, ?, ?, ?)");
				psInsert.setString(1, accType);
				psInsert.setString(2, f_Name);
				psInsert.setString(3, l_Name);
				psInsert.setString(4, username);
				psInsert.setString(5, encryptedPass);
				psInsert.setString(6, email);
				psInsert.executeUpdate();

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Registration Complete");
				alert.show();

				changeScene(event, "/memberAcc/members_signin.fxml", "Sign in");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (psCheckUser != null) {
				try {

					psCheckUser.close();

				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}

			if (psInsert != null) {
				try {
					psInsert.close();
				} catch (SQLException e3) {
					e3.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e4) {
					e4.printStackTrace();
				}
			}
		}

	}

	public static boolean changePass(String username, String oldPassword, String newPassword) throws NoSuchAlgorithmException {

		Connection connection = null;
		PreparedStatement psCheckMatch = null;
		PreparedStatement psChangePass = null;
		ResultSet rs = null;

		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryappdb", "root", "Root12345");
			psCheckMatch = connection.prepareStatement("SELECT * FROM accounts WHERE username = '" + username + "';");
			rs = psCheckMatch.executeQuery();

			if (rs.next()) {

				// Encrypts old password and checks if it matches the password in the db because MD5 doesnt have a way to decrypt
				String encryptOld = md5Encrypt.encryptPass(oldPassword);
				if (encryptOld.equals(rs.getString("password"))) {

					String encryptedPass = md5Encrypt.encryptPass(newPassword);
					psChangePass = connection.prepareStatement("UPDATE accounts SET password = '" + encryptedPass + "' WHERE username = '" + username + "';");
					psChangePass.executeUpdate();

					return true;

				} else {

					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Incorrect Username Or Password");
					alert.show();

					return false;
				}

			}

			else {

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Incorrect Username Or Password");
				alert.show();

				return false;

			}

		} catch (SQLException e) {
			e.printStackTrace();

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Something Went Wrong");
			alert.show();

			return false;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (psCheckMatch != null) {
				try {
					psCheckMatch.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (psChangePass != null) {
				try {
					psChangePass.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}

	}

	public static Accounts logIn(ActionEvent event, String userName, String password, String accType) throws NoSuchAlgorithmException {

		Accounts acc = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement ps_GetInfo = null;
		ResultSet resultSet = null;
		ResultSet rsInfo = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryappdb", "root", "Root12345");
			preparedStatement = connection.prepareStatement("SELECT password FROM accounts WHERE username = '" + userName + "' AND account_type = '" + accType + "';");
			resultSet = preparedStatement.executeQuery();

			// If username doesnt exist error displays
			if (!resultSet.isBeforeFirst()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Incorrect Username or Password");
				alert.show();

			}

			// If username does exist it encrypts the password then checks if it matches the one in the db because MD5 doesnt have a way to decrypt
			else {
				while (resultSet.next()) {
					String retrievedPassword = resultSet.getString("password");
					String encryptedPass = md5Encrypt.encryptPass(password);
					if (encryptedPass.equals(retrievedPassword)) {

						switch (accType) {

						case "Member":
							changeScene(event, "/memberAcc/members_HP.fxml", "LMA");
							acc = setCurrUser(userName);
							break;

						case "Librarian":
							changeScene(event, "/librarianAcc/librarians_HP.fxml", "LMA");
							acc = setCurrUser(userName);
							break;

						case "Admin":
							changeScene(event, "/adminAcc/admin_HP.fxml", "LMA");
							acc = setCurrUser(userName);
							break;

						}

					}

					else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("Incorrect Username or Password");
						alert.show();
					}
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			if (resultSet != null) {
				try {
					resultSet.close();

				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}

			if (rsInfo != null) {

				try {
					rsInfo.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (ps_GetInfo != null) {

				try {
					ps_GetInfo.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		}

		return acc;

	}

	public String generateRefNum() {

		String ref_num = UUID.randomUUID().toString();
		return ref_num;

	}

	public static Accounts setCurrUser(String username) {

		Connection connection = null;
		PreparedStatement psSetAcc = null;
		ResultSet rs = null;

		Accounts currUser = null;

		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryappdb", "root", "Root12345");
			psSetAcc = connection.prepareStatement("SELECT * FROM accounts WHERE username = '" + username + "';");
			rs = psSetAcc.executeQuery();

			if (rs.next()) {

				currUser = new Accounts(rs.getInt("user_id"), rs.getString("account_type"), rs.getString("f_name"), rs.getString("l_name"), rs.getString("username"), rs.getString("password"), rs.getString("email"));

			}

			else {

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (rs != null) {

				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (psSetAcc != null) {

				try {
					psSetAcc.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (connection != null) {

				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return currUser;
	}

	public static boolean cancelReserve(Records record) {
		
		Connection connection = null;
		PreparedStatement psUpdateRecord = null;
		PreparedStatement psGetMaterial = null;
		PreparedStatement psUpdateMaterial = null;
		ResultSet rsMaterial = null;
		
		int numCopies = 0;
		
		if (record.getReturned_date() == null || !record.getReturned_date().equals("Cancelled")) {
			
		
			try {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryappdb", "root", "Root12345");
				psUpdateRecord = connection.prepareStatement("UPDATE records SET returned_date = 'Cancelled', issued_date = 'Cancelled' WHERE ref_num = '" + record.getRef_num() + "';");
				psUpdateRecord.executeUpdate();
				psGetMaterial = connection.prepareStatement("SELECT copies_avail FROM material WHERE material_id = '" + record.getMaterial_id() + "';");
				rsMaterial = psGetMaterial.executeQuery();
				
				if (rsMaterial.next()) {
					numCopies = rsMaterial.getInt("copies_avail");
					numCopies += 1;
				}

				psUpdateMaterial = connection.prepareStatement("UPDATE material SET copies_avail = '" + numCopies + "' WHERE material_id = '" + record.getMaterial_id() + "';");
				psUpdateMaterial.executeUpdate();
				
				record.setReturned_date("Cancelled");
			
				return true;

			
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			} finally {
				if (rsMaterial != null) {
					try {
						rsMaterial.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (psUpdateMaterial != null) {
					try {
						psUpdateMaterial.close();
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
				if (psGetMaterial != null) {
					try {
						psGetMaterial.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		else {
			
			return false;
		}
		
	}
	
	public Boolean returnMaterial(Records records) {

		Connection connection = null;
		PreparedStatement psUpdateRecord = null;
		PreparedStatement psUpdateMaterial = null;
		PreparedStatement psCopies = null;
		PreparedStatement psCheckReturn = null;
		ResultSet rs = null;
		ResultSet rsReturned = null;

		Boolean returnM = false;
		int numCopies = 0;

		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryappdb", "root", "Root12345");

			psCheckReturn = connection.prepareStatement("SELECT * FROM records WHERE ref_num = '" + records.getRef_num() + "' AND returned_date = 'Requested'");
			rsReturned = psCheckReturn.executeQuery();

			if (rsReturned.isBeforeFirst()) {
				psUpdateRecord = connection.prepareStatement("UPDATE records SET returned_date = '" + LocalDate.now().toString() + "' WHERE ref_num = '" + records.getRef_num() + "';");
				psUpdateRecord.executeUpdate();

				psCopies = connection.prepareStatement("SELECT copies_avail FROM material WHERE material_id = '" + records.getMaterial_id() + "';");
				rs = psCopies.executeQuery();

				if (rs.next()) {
					numCopies = rs.getInt("copies_avail");
					numCopies += 1;
				}

				psUpdateMaterial = connection.prepareStatement("UPDATE material SET copies_avail = '" + numCopies + "' WHERE material_id = '" + records.getMaterial_id() + "';");
				psUpdateMaterial.executeUpdate();
			}

		} catch (SQLException e) {

			e.printStackTrace();

			return false;
		} finally {
			
			if (rsReturned != null) {
				try {
					rsReturned.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (psCheckReturn != null) {
				try {
					psCheckReturn.close();
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
			if (psUpdateMaterial != null) {
				try {
					psUpdateMaterial.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (psCopies != null) {
				try {
					psCopies.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (connection != null) {

				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			returnM = true;
		}

		return returnM;
	}

	public static boolean updateAccount(Accounts account) {

		Connection connection = null;
		PreparedStatement psUpdate = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryappdb", "root", "Root12345");
			psUpdate = connection.prepareStatement("UPDATE accounts SET f_name = '" + account.getF_name() + "', l_name = '" + account.getL_name() + "', username = '" + account.getUsername() + "', email = '" + account.getEmail()
					+ "' WHERE user_id = '" + account.getUser_id() + "';");
			psUpdate.executeUpdate();

			return true;

		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		} finally {
			if (psUpdate != null) {

				try {
					psUpdate.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {

				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public static boolean updateMaterial(Material material) {

		Connection connection = null;
		PreparedStatement psUpdate = null;

		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryappdb", "root", "Root12345");
			psUpdate = connection.prepareStatement("UPDATE material SET title = '" + material.getTitle() + "', author = '" + material.getAuthor() + "', genre = '" + material.getGenre() + "', isbn = '" + material.getIsbn() + "', copies_tot = '"
					+ material.getCopies_tot() + "', copies_avail = '" + material.getCopies_avail() + "' WHERE material_id = '" + material.getMaterial_id() + "';");
			psUpdate.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (psUpdate != null) {

				try {
					psUpdate.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {

				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return false;
	}

	public static boolean addAccount(String acctype, String fName, String lName, String username, String password, String email) throws NoSuchAlgorithmException {

		Connection connection = null;
		PreparedStatement psAdd = null;
		PreparedStatement psCheckUsername = null;
		ResultSet rs = null;

		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryappdb", "root", "Root12345");

			psCheckUsername = connection.prepareStatement("SELECT * FROM accounts WHERE username = '" + username + "';");
			rs = psCheckUsername.executeQuery();

			if (rs.isBeforeFirst()) {

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Username already exists");
				alert.show();

				return false;
			} else {
				String encryptedPass = md5Encrypt.encryptPass(password);

				psAdd = connection.prepareStatement("INSERT INTO accounts (account_type, f_name, l_name, username, password, email) VALUES (?, ?, ?, ?, ?, ?)");
				psAdd.setString(1, acctype);
				psAdd.setString(2, fName);
				psAdd.setString(3, lName);
				psAdd.setString(4, username);
				psAdd.setString(5, encryptedPass);
				psAdd.setString(6, email);
				psAdd.executeUpdate();

				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();

			return false;

		} finally {
			if (rs != null) {

				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (psAdd != null) {
				try {
					psAdd.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (psCheckUsername != null) {

				try {
					psCheckUsername.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (connection != null) {

				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public static boolean addMaterial(Material material) {

		Connection connection = null;
		PreparedStatement psCheck = null;
		PreparedStatement psAdd = null;
		ResultSet rs = null;

		
		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryappdb", "root", "Root12345");
			
			psCheck = connection.prepareStatement("SELECT * FROM material WHERE title = '" + material.getTitle() + "' AND author = '" + material.getAuthor() + "';");
			rs = psCheck.executeQuery();
			
			if (rs.next()) {
				
				return false;
			}
			
			else {
				psAdd = connection.prepareStatement("INSERT INTO material (title, author, material_type, genre, isbn, copies_tot, copies_avail) VALUES (?, ?, ?, ?, ?, ?, ?)");
				psAdd.setString(1, material.getTitle());
				psAdd.setString(2, material.getAuthor());
				psAdd.setString(3, material.getMaterial_type());
				psAdd.setString(4, material.getGenre());
				psAdd.setString(5, material.getIsbn());
				psAdd.setInt(6, material.getCopies_tot());
				psAdd.setInt(7, material.getCopies_tot());
				psAdd.executeUpdate();

				return true;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (psAdd != null) {
				try {
					psAdd.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {

				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return false;
	}

	// Function to return true if material was reserved and uodate database
	// accordingly
	public Boolean reserveMaterial(Records materialRecord) {

		Connection connection = null;
		PreparedStatement psAddRecord = null;
		PreparedStatement psUpdateCopies = null;

		int copiesCnt = materialRecord.getMaterial().getCopies_avail();

		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryappdb", "root", "Root12345");
			psAddRecord = connection.prepareStatement("INSERT INTO records (ref_num, user_id, acc_type, material_id, material_type, material_title, material_author, ISBN, reserved_date, due_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			psAddRecord.setString(1, generateRefNum());
			psAddRecord.setInt(2, materialRecord.getUser_id());
			psAddRecord.setString(3, materialRecord.getAcc_type());
			psAddRecord.setInt(4, materialRecord.getMaterial_id());
			psAddRecord.setString(5, materialRecord.getMaterial_type());
			psAddRecord.setString(6, materialRecord.getMaterial_title());
			psAddRecord.setString(7, materialRecord.getMaterial_author());
			psAddRecord.setString(8, materialRecord.getIsbn());
			psAddRecord.setString(9, materialRecord.getReserved_date());
			psAddRecord.setString(10, materialRecord.getDue_date());
			psAddRecord.executeUpdate();

			copiesCnt -= 1;
			psUpdateCopies = connection.prepareStatement("UPDATE material SET copies_avail = '" + copiesCnt + "' WHERE material_id = '" + materialRecord.getMaterial().getMaterial_id() + "' ;");
			psUpdateCopies.executeUpdate();

			members_HP_controller.material.setCopies_avail(copiesCnt);

			return true;

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			if (psAddRecord != null) {
				try {
					psAddRecord.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

			if (psUpdateCopies != null) {

				try {
					psUpdateCopies.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}

			if (connection != null) {

				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return false;

	}

	// Allows a user to reserve a book even if he has reserved it before by checking
	// if it has been returned
	public Boolean checkIfReturned(Material material, Integer user_id) {

		Connection connection = null;
		PreparedStatement psCheckReturn = null;
		ResultSet rs = null;
		boolean returned = false;

		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryappdb", "root", "Root12345");
			psCheckReturn = connection.prepareStatement(
					"SELECT * FROM records WHERE user_id = '" + user_id + "' AND material_id = '" + material.getMaterial_id() + "' AND returned_date IS NULL OR returned_date = '' OR returned_date = ' ' OR returned_date = 'Requested';");
			rs = psCheckReturn.executeQuery();

			returned = rs.next();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (psCheckReturn != null) {

				try {
					psCheckReturn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (connection != null) {

				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return returned;

	}

	// Prevents user from reserving material more than once
	public Boolean checkIfReserved(Material material, Integer user_id) {

		Connection connection = null;
		PreparedStatement psCheckReserved = null;
		ResultSet rs = null;
		boolean reserved = false;

		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryappdb", "root", "Root12345");
			psCheckReserved = connection.prepareStatement("SELECT * FROM records WHERE material_id = '" + material.getMaterial_id() + "' AND user_id = '" + user_id + "' AND reserved_date IS NOT NULL;");
			rs = psCheckReserved.executeQuery();
			reserved = rs.next();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

			if (psCheckReserved != null) {

				try {
					psCheckReserved.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {

				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return reserved;

	}

	public static Double getFee(String refNum) {

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double fee = 0;

		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryappdb", "root", "Root12345");
			ps = connection.prepareStatement("SELECT * FROM records WHERE ref_num = '" + refNum + "'");
			rs = ps.executeQuery();

			if (rs.next()) {
				String temp = rs.getString("due_date");
				String due = temp.replaceAll("\\-", "");
				String temp2 = LocalDate.now().toString();
				String currDate = temp2.replaceAll("\\-", "");
				int x = Integer.valueOf(due);
				int y = Integer.valueOf(currDate);

				/*
				 * Checks if current date is greater than due date If it is it adds 17cents each
				 * late date Caps out at $15
				 */
				if (y > x) {

					int days = y - x;
					fee = days * 0.17;

					if (fee > 15) {

						fee = 15;
					}
				}

				else {
					fee = 0;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return fee;

	}

}
