package view;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Products;

// TODO: Auto-generated Javadoc
/**
 * The Class orderKontrolleri.
 */
public class orderKontrolleri implements Initializable {

	/** The controller. */
	Controller controller = Controller.Singleton();

	/** The myynti QTY field. */
	@FXML
	private TextField myyntiQTYField;

	/** The order button. */
	@FXML
	private Button orderButton;
	
	@FXML
	private Label orderLabel;
	
	private ResourceBundle bundle;

	private Locale locale;

	private void loadlang(String lang) {
		locale = new Locale(lang);
		bundle = ResourceBundle.getBundle("view.lang", locale);
		orderLabel.setText(bundle.getString("orderLabel"));

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

	/**
	 * Gets the order QTY.
	 *
	 * @return the order QTY
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public int getOrderQTY() throws IOException {
		String toNum = myyntiQTYField.getText();
		if (isNumeric(toNum)) {
			Stage stage = (Stage) orderButton.getScene().getWindow();
			stage.close();
			return Integer.parseInt(toNum);
		} else {
			viewAlert();
			return 0;
		}
	}

	/**
	 * Order QTY.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void orderQTY() throws IOException {
		if (controller.getProduct() == null) {
			System.out.println("View - addOrderItem(): ei tuotetta");
		} else {
			Products p = controller.getProduct();
			int qty = getOrderQTY();
			controller.setOrderQty(qty);
			p.setQuantity(qty);
			controller.addTilausTuote(p);
		}
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
