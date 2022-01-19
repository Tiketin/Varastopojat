package view;

import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import controller.Controller;
import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Products;
import model.Purchase;
import model.Tabledata;
import model.OstoTabledata;

/**
 * The Class InventaariKontrolleri.
 */
public class InventaariKontrolleri implements Initializable {

	Controller controller = Controller.Singleton();

	/**
	 * https://stackoverflow.com/questions/14059954/calling-view-method-in-controller
	 */
	private static InventaariKontrolleri instance;
	public InventaariKontrolleri() {
		instance = this;
	}
	public static InventaariKontrolleri getInstance() {
		return instance;
	}
	
	

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

	@FXML
	private Button lisaaButton;

	@FXML
	private Button paivitaButton;

	@FXML
	private Button poistaButton;

	@FXML
	private Button saldoButton;

	@FXML
	private TextField hakuField;

	@FXML
	private Button myyntiLisaaTuoteButton;

	@FXML
	private Button myyntiTeeTilausButton;

	@FXML
	private TextField asiakkaanNimiField;

	@FXML
	private TextField asiakkaanOsoiteField;

	@FXML
	private TextField asiakkaanKaupunkiField;

	@FXML
	private TextField asiakkaanPostinumeroField;

	@FXML
	private TextField myyntiQTYField;

	@FXML
	private Button orderButton;

	@FXML
	private Button teeOstoButton;

	@FXML
	private Button luoToimittajaButton;

	@FXML
	private Button poistaOstoButton;

	@FXML
	private Button poistaToimittajaButton;

	@FXML
	private Button ostoTilausButton;

	@FXML
	private Label languageLabel;

	@FXML
	private Tab settingsTab;

	@FXML
	private Tab billingTab;

	@FXML
	private Tab orderTab;

	@FXML
	private Tab purchaseTab;

	@FXML
	private Tab inventoryTab;

	@FXML
	private Label productSearchLabel;
	
	@FXML
	private Label customerNameLabel;
	
	@FXML
	private Label customerAddressLabel;
	
	@FXML
	private Label customerCityLabel;
	
	@FXML
	private Label customerZipLabel;

	private ResourceBundle bundle;

	private Locale locale;

	@FXML
	private void btnFI() {
		loadLang("fi");
		controller.setFiLang();
	}

	@FXML
	private void btnEN() {
		loadLang("en");
		controller.setEnLang();
	}

	private void loadLang(String lang) {
		locale = new Locale(lang);
		bundle = ResourceBundle.getBundle("view.lang", locale);
		settingsTab.setText(bundle.getString("settingsTab"));
		billingTab.setText(bundle.getString("billingTab"));
		orderTab.setText(bundle.getString("orderTab"));
		purchaseTab.setText(bundle.getString("purchaseTab"));
		inventoryTab.setText(bundle.getString("inventoryTab"));
		col_name.setText(bundle.getString("col_name"));
		col_qty.setText(bundle.getString("col_qty"));
		col_price.setText(bundle.getString("col_price"));
		productSearchLabel.setText(bundle.getString("productSearchLabel"));
		hakuField.setPromptText(bundle.getString("hakuField"));
		lisaaButton.setText(bundle.getString("lisaaButton"));
		poistaButton.setText(bundle.getString("poistaButton"));
		saldoButton.setText(bundle.getString("saldoButton"));
		ostoTilausButton.setText(bundle.getString("ostoTilausButton"));
		col_ostoTuoteId.setText(bundle.getString("col_ostoTuoteId"));
		col_ostoQty.setText(bundle.getString("col_ostoQty"));
		col_ostoDate.setText(bundle.getString("col_ostoDate"));
		col_ostoSupplier.setText(bundle.getString("col_ostoSupplier"));
		luoToimittajaButton.setText(bundle.getString("luoToimittajaButton"));
		poistaOstoButton.setText(bundle.getString("poistaOstoButton"));
		poistaToimittajaButton.setText(bundle.getString("poistaToimittajaButton"));
		col_name1.setText(bundle.getString("col_name1"));
		col_qty1.setText(bundle.getString("col_qty1"));
		col_price1.setText(bundle.getString("col_price1"));
		myyntiLisaaTuoteButton.setText(bundle.getString("myyntiLisaaTuoteButton"));
		col_name2.setText(bundle.getString("col_name2"));
		col_qty2.setText(bundle.getString("col_qty2"));
		col_price2.setText(bundle.getString("col_price1"));
		customerNameLabel.setText(bundle.getString("customerNameLabel"));
		customerAddressLabel.setText(bundle.getString("customerAddressLabel"));
		customerCityLabel.setText(bundle.getString("customerCityLabel"));
		customerZipLabel.setText(bundle.getString("customerZipLabel"));
		asiakkaanNimiField.setPromptText(bundle.getString("asiakkaanNimiField"));
		asiakkaanOsoiteField.setPromptText(bundle.getString("asiakkaanOsoiteField"));
		asiakkaanKaupunkiField.setPromptText(bundle.getString("asiakkaanKaupunkiField"));
		asiakkaanPostinumeroField.setPromptText(bundle.getString("asiakkaanPostinumeroField"));
		myyntiTeeTilausButton.setText(bundle.getString("myyntiTeeTilausButton"));
		

	}

	@FXML
	TableView<Tabledata> tableview;

	@FXML
	TableView<Tabledata> tableview1;

	@FXML
	TableView<Tabledata> tableview2;

	@FXML
	TableView<OstoTabledata> tableview3;

	@FXML
	TableColumn<Tabledata, Integer> col_id;

	@FXML
	TableColumn<Tabledata, Integer> col_id1;

	@FXML
	TableColumn<Tabledata, Integer> col_id2;

	@FXML
	TableColumn<Tabledata, String> col_name;

	@FXML
	TableColumn<Tabledata, String> col_name1;

	@FXML
	TableColumn<Tabledata, String> col_name2;

	@FXML
	TableColumn<Tabledata, Integer> col_qty;

	@FXML
	TableColumn<Tabledata, Integer> col_qty1;

	@FXML
	TableColumn<Tabledata, Integer> col_qty2;

	@FXML
	TableColumn<Tabledata, Double> col_price;

	@FXML
	TableColumn<Tabledata, Double> col_price1;

	@FXML
	TableColumn<Tabledata, Double> col_price2;

	@FXML
	TableColumn<OstoTabledata, Integer> col_ostoID;

	@FXML
	TableColumn<OstoTabledata, Integer> col_ostoTuoteId;

	@FXML
	TableColumn<OstoTabledata, Integer> col_ostoQty;

	@FXML
	TableColumn<OstoTabledata, String> col_ostoDate;

	@FXML
	TableColumn<OstoTabledata, Integer> col_ostoSupplier;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	private final ObservableList<Tabledata> data = FXCollections.observableArrayList();

	private final ObservableList<OstoTabledata> ostoData = FXCollections.observableArrayList();

	private final ObservableList<Tabledata> data2 = FXCollections.observableArrayList();

	/**
	 * View add product.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void viewAddProduct() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("addProduct.fxml"));		
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
		System.out.println("viewAddProduct()");
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
		stage.setResizable(false);
	}
	
	/**
	 * View remove product.
	 */
	public void viewRemoveProduct(){
		int i = tableview.getSelectionModel().getSelectedItem().getId();
		System.out.println("Poistetaan " + i);
		controller.deleteProduct(i);
		System.out.println("Poistettiin " + i);
		refreshTable();
	}
	
	/**
	 * View update QTY.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void viewUpdateQTY() throws IOException {
		int i = tableview.getSelectionModel().getSelectedItem().getId();
		controller.setProductId(i);
		Parent root = FXMLLoader.load(getClass().getResource("saldo.fxml"));		
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
		System.out.println("viewUpdateQTY()");
	}
	
	/**
	 * View add order.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void viewAddOrder() throws IOException {
		int id = tableview1.getSelectionModel().getSelectedItem().getId();
		controller.setProductId(id);
		Parent root = FXMLLoader.load(getClass().getResource("order.fxml"));		
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
		System.out.println("viewAddOrder()");
	}
	
	/**
	 * View add supplier.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void viewAddSupplier() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("addSupplier.fxml"));		
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
	}
	
	/**
	 * View osto tilaus.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void viewOstoTilaus() throws IOException {
		int id = tableview.getSelectionModel().getSelectedItem().getId();
		controller.setProductId(id);
		System.out.println("viewOstoTilaus()");
		Parent root = FXMLLoader.load(getClass().getResource("Ostotilaus.fxml"));		
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
	}
	
	/**
	 * Gets the products.
	 *
	 * @return the products
	 */
	public Products[] getProducts() {
		return controller.getProducts();
	}
	
	/**
	 * Refresh table.
	 */
	public void refreshTable() {
		LocalDateTime time = LocalDateTime.now();
		String formattedTime = time.format(formatter);
		data.clear();
		data2.clear();
		ostoData.clear();
		addProductsToList(getProducts());
		addOrderDetailsToList();
		addPurchaseDetailsToList();
		tableview.refresh();
		tableview1.refresh();
		tableview2.refresh();
		tableview3.refresh();
	}	
	
	/**
	 * Adds the order details to list.
	 */
	public void addOrderDetailsToList() {
		List<Products> list = controller.returnTilausTuote();
		for(int i = 0; i < list.size(); i++) {
			data2.add(new Tabledata(list.get(i).getProductId(), list.get(i).getName(), list.get(i).getQuantity(), list.get(i).getPrice()));
		}
	}
	
	/**
	 * Adds the purchase details to list.
	 */
	public void addPurchaseDetailsToList() {
		List<Purchase> list = controller.returnOstoTuote();
		for(int i = 0; i < list.size(); i++) {
			int purchaseId = list.get(i).getPurchaseId();
			int productId = list.get(i).getProductId();
			int qty = list.get(i).getQty();
			LocalDateTime time = list.get(i).getLocalDate();
			int supplierId = list.get(i).getSupplierId();
			String formattedTime = time.format(formatter);
			System.out.println("id: " + purchaseId + " productId: " + productId + " qty: " + qty + " time: " + formattedTime + " supplier: " + supplierId);
			ostoData.add(new OstoTabledata(purchaseId, productId, qty, formattedTime, supplierId));
		}
	}
	
	/**
	 * Adds the products to list.
	 *
	 * @param productList the product list
	 */
	public void addProductsToList(Products[] productList) {
		for (Products p : productList) {
			data.add(new Tabledata(p.getProductId(), p.getName(), p.getQuantity(), p.getPrice()));
			}
	}
	public void deleteOstoTilaus() {
		int i = tableview3.getSelectionModel().getSelectedItem().getPurchaseId();
		System.out.println("Poistetaan " + i);
		controller.deleteOstoTilaus(i);
		System.out.println("Poistettiin " + i);
		refreshTable();
	}

	/**
	 * Adds the order.
	 */
	public void addOrder() {
		System.out.println("View - addOrder()");
		String asiakasNimi = asiakkaanNimiField.getText();
		String osoite = asiakkaanOsoiteField.getText();
		String kaupunki = asiakkaanKaupunkiField.getText();
		List<Products> tilatuttuotteet = new ArrayList<>();
		for(int i = 0; i < data2.size(); i++) {
			Products p = new Products(data2.get(i).getId(), data2.get(i).getName(), data2.get(i).getQty(), data2.get(i).getPrice());
			tilatuttuotteet.add(p);
		}
		controller.createOrder(asiakasNimi, osoite, kaupunki, tilatuttuotteet);	
	}


	/**
	 * Initialize.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		
		col_id.setCellValueFactory(new PropertyValueFactory<Tabledata, Integer>("id"));
		col_id1.setCellValueFactory(new PropertyValueFactory<Tabledata, Integer>("id"));
		col_id2.setCellValueFactory(new PropertyValueFactory<Tabledata, Integer>("id"));
		col_name.setCellValueFactory(new PropertyValueFactory<Tabledata, String>("name"));
		col_name1.setCellValueFactory(new PropertyValueFactory<Tabledata, String>("name"));
		col_name2.setCellValueFactory(new PropertyValueFactory<Tabledata, String>("name"));
		col_qty.setCellValueFactory(new PropertyValueFactory<Tabledata, Integer>("qty"));
		col_qty1.setCellValueFactory(new PropertyValueFactory<Tabledata, Integer>("qty"));
		col_qty2.setCellValueFactory(new PropertyValueFactory<Tabledata, Integer>("qty"));
		col_price.setCellValueFactory(new PropertyValueFactory<Tabledata, Double>("price"));
		col_price1.setCellValueFactory(new PropertyValueFactory<Tabledata, Double>("price"));
		col_price2.setCellValueFactory(new PropertyValueFactory<Tabledata, Double>("price"));
		col_ostoID.setCellValueFactory(new PropertyValueFactory<OstoTabledata, Integer>("purchaseId"));
		col_ostoTuoteId.setCellValueFactory(new PropertyValueFactory<OstoTabledata, Integer>("productId"));
		col_ostoQty.setCellValueFactory(new PropertyValueFactory<OstoTabledata, Integer>("qty"));
		col_ostoDate.setCellValueFactory(new PropertyValueFactory<OstoTabledata, String>("date"));
		col_ostoSupplier.setCellValueFactory(new PropertyValueFactory<OstoTabledata, Integer>("supplierId"));		
		addProductsToList(getProducts());
		addOrderDetailsToList();
		addPurchaseDetailsToList();
		tableview.setItems(data);
		tableview1.setItems(data);
		tableview2.setItems(data2);
		tableview3.setItems(ostoData);
		//refresh();
		
		//Tuotehaun määrittely
		FilteredList<Tabledata> filteredData = new FilteredList<>(data, b -> true);
		
		hakuField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Tabledata -> {
				
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Tabledata.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;					
				} else {
					return false;
				}
			});
		});
		
		SortedList<Tabledata> sortedData = new SortedList<>(filteredData);
		
		sortedData.comparatorProperty().bind(tableview.comparatorProperty());
		
		tableview.setItems(sortedData);

		System.out.println("initialize()");

	}	
}


