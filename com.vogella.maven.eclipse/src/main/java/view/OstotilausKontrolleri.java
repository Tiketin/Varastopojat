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

// TODO: Auto-generated Javadoc
/**
 * The Class OstotilausKontrolleri.
 */
public class OstotilausKontrolleri implements Initializable {

	/** The controller. */
	Controller controller = Controller.Singleton();

	/** Product quantity field. */
	@FXML
	private TextField tuotteenMaaraField;

	/** Supplier id field. */
	@FXML
	private TextField toimittajanIDField;

	/** Purchase button. Calls the addOstotilaus() method */
	@FXML
	private Button ostotilausButton;

	@FXML
	private Label tuotteenMaaraLabel;

	@FXML
	private Label toimittajanIDLabel;

	private ResourceBundle bundle;

	private Locale locale;

	private void loadlang(String lang) {
		locale = new Locale(lang);
		bundle = ResourceBundle.getBundle("view.lang", locale);
		tuotteenMaaraField.setPromptText(bundle.getString("tuotteenMaaraField"));
		tuotteenMaaraLabel.setText(bundle.getString("tuotteenMaaraLabel"));
		toimittajanIDLabel.setText(bundle.getString("toimittajanIDLabel"));

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
	 * Adds the ostotilaus.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * 
	 *                     ostotilausButton calls this method.
	 */

	public void addOstotilaus() throws IOException {
		System.out.println("addOstotilaus");
		String sqty = tuotteenMaaraField.getText();
		String supplier = toimittajanIDField.getText();
		if (isNumeric(sqty) && isNumeric(supplier)) {
			int qty = Integer.parseInt(sqty);
			int supplierId = Integer.parseInt(supplier);
			controller.createPurchase(qty, supplierId);
			controller.update();
			Stage stage = (Stage) ostotilausButton.getScene().getWindow();
			stage.close();
		} else {
			viewAlert();
		}

	}

	/**
	 * Checks if the input string is numeric.
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
