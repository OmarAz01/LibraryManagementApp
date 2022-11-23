package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//DB connection class allows connection to the db outside of DB.java class
public class DBConnection {

	Connection databaseLink = null;

	public Connection getDBConnection() {

		try {

			String databaseUser = "root";
			String databasePassword = "Root12345";
			String url = "jdbc:mysql://localhost:3306/libraryappdb";

			Class.forName("com.mysql.cj.jdbc.Driver");
			databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return databaseLink;

	}

}
