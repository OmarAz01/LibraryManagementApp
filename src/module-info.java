module LibraryManagementApp {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	
	opens adminAcc to javafx.graphics, javafx.fxml, javafx.base;
	
	opens librarianAcc to javafx.graphics, javafx.fxml, javafx.base;
	
	opens memberAcc to javafx.graphics, javafx.fxml, javafx.base;
	
	opens models to javafx.graphics, javafx.fxml, javafx.base;
}
