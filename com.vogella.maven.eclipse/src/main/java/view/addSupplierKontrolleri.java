package view;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The Class addSupplierKontrolleri.
 */
public class addSupplierKontrolleri implements Initializable {

	/** The controller. */
	Controller controller = Controller.Singleton();

	/** The supplier name field. */
	@FXML
	private TextField supplierNameField;

	/** The supplier button. */
	@FXML
	private Button supplierButton;

	/** The supplier address field. */
	@FXML
	private TextField supplierAddressField;
	
	@FXML
	private Label addSupplierLabel;
	
	private ResourceBundle bundle;

	private Locale locale;

	private void loadlang(String lang) {
		locale = new Locale(lang);
		bundle = ResourceBundle.getBundle("view.lang", locale);
		addSupplierLabel.setText(bundle.getString("addSupplierLabel"));
		supplierNameField.setPromptText(bundle.getString("supplierNameField"));
		supplierAddressField.setPromptText(bundle.getString("supplierAddressField"));

	}

	/**
	 * Adds the supplier.
	 */
	// tämä metodi on asetettu "ok" nappiin
	public void addSupplier() {
		String name = supplierNameField.getText();
		String address = supplierAddressField.getText();
		controller.createSupplier(name, address);
		Stage stage = (Stage) supplierButton.getScene().getWindow();
		stage.close();
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
