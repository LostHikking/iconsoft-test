package io.github.losthikking.iconsofttest;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
		primaryStage.setOnCloseRequest(t -> {
			Platform.exit();
			System.exit(0);
		});
		primaryStage.setTitle("Chat");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
