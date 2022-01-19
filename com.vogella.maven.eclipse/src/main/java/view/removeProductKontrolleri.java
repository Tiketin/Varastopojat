package view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.stage.Stage;
import model.Products;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import controller.*;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

/**
 * The Class removeProductKontrolleri.
 */
public class removeProductKontrolleri implements IRemoveProductGUI, Initializable {

	Controller controller = Controller.Singleton();

	@FXML
	private TextField idField;

	@FXML
	private TextField nimiField;

	@FXML
	private Label idWarning;

	@FXML
	private Button deleteButton;

	@FXML
	private Button alertOKButton;
	
	@FXML
	private Label alertLabel;
	
	private ResourceBundle bundle;

	private Locale locale;

	private void loadlang(String lang) {
		locale = new Locale(lang);
		bundle = ResourceBundle.getBundle("view.lang", locale);
		alertLabel.setText(bundle.getString("alertLabel"));
	}

	/**
	 * View alert.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void viewAlert() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("alert.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
	}

	/**
	 * Close alert.
	 */
	public void closeAlert() {
		Stage stage = (Stage) alertOKButton.getScene().getWindow();
		stage.close();
	}

	/*
	 * private void idFieldKeyPressed(KeyEvent evt) { String c = evt.getCharacter();
	 * if(suklaapuuro) { idField.setEditable(false);
	 * idWarning.setText("Syötä numeroita"); } else { idField.setEditable(true); } }
	 */

	/**
	 * Gets the delete product ID.
	 *
	 * @return the delete product ID
	 */
	public int getDeleteProductID() {
		if (isNumeric(idField.getText())) {
			return Integer.parseInt(idField.getText());
		} else {
			return 0;
		}
	}

	/*
	 * @Override public String getDeleteProductName() { return nimiField.getText();
	 * }
	 */

	/**
	 * Delete product.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void deleteProduct() throws IOException {
		int id = getDeleteProductID();
		boolean delete = controller.deleteProduct(id);
		if (delete) {
			Stage stage = (Stage) deleteButton.getScene().getWindow();
			stage.close();
		} else {
			viewAlert();
		}
	}

	/**
	 * Checks if is numeric.
	 *
	 * @param strNum the str num
	 * @return true, if is numeric
	 */
	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			int d = Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (controller.getLang().equals("fi")) {
			loadlang("fi");
		} else {
			loadlang("en");
		}
	}

}
