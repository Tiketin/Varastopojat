package view;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import com.mysql.fabric.xmlrpc.base.Data;

import controller.Controller;
import controller.IController;
import javafx.collections.FXCollections;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The Class addProductKontrolleri.
 */
public class addProductKontrolleri implements IAddProductGUI, Initializable {

	/** The controller. */
	Controller controller = Controller.Singleton();

	/** The nimi field. */
	@FXML
	private TextField nimiField;

	/** The qty field. */
	@FXML
	private TextField qtyField;

	/** The hinta field. */
	@FXML
	private TextField hintaField;

	/** The lisaa button 2. */
	@FXML
	private Button lisaaButton2;

	@FXML
	private Label addProductName;

	@FXML
	private Label addProductQTY;

	@FXML
	private Label addProductPrice;

	private ResourceBundle bundle;

	private Locale locale;

	private void loadlang(String lang) {
		locale = new Locale(lang);
		bundle = ResourceBundle.getBundle("view.lang", locale);
		addProductPrice.setText(bundle.getString("addProductPrice"));
		addProductQTY.setText(bundle.getString("addProductQTY"));
		addProductName.setText(bundle.getString("addProductName"));
		lisaaButton2.setText(bundle.getString("lisaaButton2"));
	}

	/**
	 * Gets the new products name.
	 *
	 * @return the new products name
	 */
	@Override
	public String getNewProductsName() {
		return nimiField.getText();
	}

	/**
	 * Gets the new products quantity.
	 *
	 * @return the new products quantity
	 */
	@Override
	public int getNewProductsQuantity() {
		return Integer.parseInt(qtyField.getText());
	}

	/**
	 * Gets the new products price.
	 *
	 * @return the new products price
	 */
	@Override
	public double getNewProductsPrice() {
		return Double.valueOf(hintaField.getText());
	}

	/**
	 * Adds the product.
	 */
	@Override
	public void addProduct() {
		controller.addNewProduct(getNewProductsName(), getNewProductsQuantity(), getNewProductsPrice());
		Stage stage = (Stage) lisaaButton2.getScene().getWindow();
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
