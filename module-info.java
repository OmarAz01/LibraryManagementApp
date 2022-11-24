module LibraryManagementApp {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base, java.sql;
	
	opens adminAcc to javafx.graphics, javafx.fxml, javafx.base, java.sql;
	
	opens librarianAcc to javafx.graphics, javafx.fxml, javafx.base, java.sql;
	
	opens memberAcc to javafx.graphics, javafx.fxml, javafx.base, java.sql;
	
	opens models to javafx.graphics, javafx.fxml, javafx.base, java.sql;
}
