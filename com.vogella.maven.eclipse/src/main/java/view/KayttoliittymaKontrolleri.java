package view;

import javafx.scene.control.Button;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import model.Tabledata;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.criteria.Root;

import javafx.beans.binding.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KayttoliittymaKontrolleri {

	@FXML
	private Button inventaariButton;

	@FXML
	private Button ostoButton;

	@FXML
	private Button myyntiButton;

	@FXML
	private Button laskutusButton;

	@FXML
	private Button hakuButton;

	/**
	 * View inventaari.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void viewInventaari() throws IOException {
		Stage stage = (Stage) inventaariButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("InventaariV3.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		primaryStage.setResizable(false);
	}

	/**
	 * View search.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void viewSearch() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Search.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}

}
