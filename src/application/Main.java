package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import models.Accounts;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

	public static Accounts currUser;

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/application/application.fxml"));
		primaryStage.setTitle("LMA");
		primaryStage.setScene(new Scene(root, 704, 472));
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
