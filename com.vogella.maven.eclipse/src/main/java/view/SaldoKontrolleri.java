package view;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import controller.Controller;
import controller.IController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SaldoKontrolleri implements IUpdateQtyGUI, Initializable {

	Controller controller = Controller.Singleton();

	@FXML
	private Label currentQTY;

	@FXML
	private TextField qtyField;

	@FXML
	private Button updateButton;

	@FXML
	private Label currentQTYLabel;

	@FXML
	private Label newQTYLabel;

	private ResourceBundle bundle;

	private Locale locale;

	private void loadlang(String lang) {
		locale = new Locale(lang);
		bundle = ResourceBundle.getBundle("view.lang", locale);
		currentQTYLabel.setText(bundle.getString("currentQTYLabel"));
		newQTYLabel.setText(bundle.getString("newQTYLabel"));
		updateButton.setText(bundle.getString("updateButton"));

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
	 * Gets the updated QTY.
	 *
	 * @return the updated QTY
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public int getUpdatedQTY() throws IOException {
		String toNum = qtyField.getText();
		if (isNumeric(toNum)) {
			Stage stage = (Stage) updateButton.getScene().getWindow();
			stage.close();
			return Integer.parseInt(qtyField.getText());
		} else {
			viewAlert();
			return controller.getProduct().getQuantity();
		}
	}

	/**
	 * Update QTY.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void updateQTY() throws IOException {
		int i = getUpdatedQTY();
		System.out.println("updateQTY()");
		controller.changeQty(i);
		System.out.println("Uusi saldo: " + i);
	}

	/**
	 * Initialize.
	 *
	 * @param location  the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String qty = Integer.toString(controller.getProduct().getQuantity());
		currentQTY.setText(qty);

		if (controller.getLang().equals("fi")) {
			loadlang("fi");
		} else {
			loadlang("en");
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

}
