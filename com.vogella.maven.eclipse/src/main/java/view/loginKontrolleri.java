package view;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class loginKontrolleri {

	@FXML
	private TextField tunnus;

	@FXML
	private TextField salasana;

	@FXML
	private Button kirjauduButton;

	public void viewMenu() throws IOException {
		Stage stage = (Stage) kirjauduButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("InventaariV3.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		//primaryStage.setResizable(false);
	}
}
