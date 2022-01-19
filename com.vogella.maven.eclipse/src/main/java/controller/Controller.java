package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.*;
import view.IUpdateQtyGUI;
import view.InventaariKontrolleri;
import view.IAddProductGUI;
import view.IRemoveProductGUI;

/**
 * The Class Controller.
 *
 * @author Nikke Tikka, Pauli Vuolle-Apiala
 *
 */

public class Controller implements IController {
	/** The model. */
	private IStorageModel model = new StorageModel();
	private IOrdersDAO orders = new OrdersAccessObject();
	private ISupplierDAO supplier = new SupplierAccessObject();
	private IPurchaseDAO purchase = new PurchaseAccessObject();
	
	/** The product id. */
	private int productId;
	private int orderQty;
	private String lang = "fi";
	private List<Products> tilausTuote = new ArrayList<>();
	
	
	/** The single instance. */
	private static Controller single_instance = null;
	
	/**
	 * Instantiates a new controller.
	 */
	private Controller() {
	}
	
	/**
	 * Singleton.
	 *
	 * @return the controller
	 */
	public static Controller Singleton() 
    { 
        // To ensure only one instance is created 
        if (single_instance == null) 
        { 
            single_instance = new Controller(); 
        } 
        return single_instance; 
    }  
	
	/**
	 * Change qty.
	 *
	 * @param qty the qty
	 */
	public void changeQty(int qty) {
		System.out.println("controller - productID: " + productId);
		System.out.println("controller - productQTY: " + qty);
		model.changeQty(productId, qty);		
	}
	public void changeQty(int id, int qty) {
		System.out.println("controller - productID: " + productId);
		System.out.println("controller - productQTY: " + qty);
		model.changeQty(id, qty);		
	}
	
	public void setOrderQty(int qty) {
		System.out.println("controller - productID: " + productId);
		System.out.println("controller - orderQTY: " + qty);
		orderQty = qty;		
	}
	public int getOrderQty() {
		return orderQty;
	}
	public void addTilausTuote(Products p) {
		tilausTuote.add(p);
		InventaariKontrolleri.getInstance().refreshTable();
	}
	public void deleteOstoTilaus(int purchaseId) {
		purchase.deletePurchase(purchaseId);
	}
	public List<Products> returnTilausTuote(){
		return tilausTuote;
	}
	public List<Purchase> returnOstoTuote(){
		List<Purchase> ostoTuote = new ArrayList<>();
		Purchase[] p = purchase.readPurchases();
		for(int i = 0; i < p.length; i++) {
			ostoTuote.add(p[i]);
		}
		return ostoTuote;
	}
	public int createPurchase(int qty, int supplierId) {
		Purchase p = new Purchase();
		p.setLocalDate(LocalDateTime.now());
		p.setQty(qty);
		p.setPurchasedProductId(productId);
		p.setSupplier(supplierId);
		double productPrice = model.getProduct(productId).getPrice();
		double totalPrice = productPrice * qty;
		p.setPurchaseCost(totalPrice);
		int purchaseId = purchase.createPurchase(p);
		return purchaseId;
	}
	public int createOrder(String asiakasNimi, String osoite, String kaupunki, List<Products> tilatutTuotteet) {
		int fail = orders.createOrder(asiakasNimi, osoite, kaupunki, tilatutTuotteet);	
		if(fail == -1) {
			System.out.println("Controller - Tilauksen luonti epäonnistui");
		}
		else {
			for(int i = 0; i < tilatutTuotteet.size(); i ++) {			
				int id = tilatutTuotteet.get(i).getProductId();
				int orderQty = tilatutTuotteet.get(i).getQuantity();
				Products p = getProduct(id);
				int newQty = p.getQuantity() - orderQty;
				model.changeQty(id, newQty);
			}
			tilausTuote.clear();
			System.out.println("Controller - Tilaus luotu, id: " + fail);			
		}
		return fail;
	}
	public int createSupplier(String name, String address) {
		Supplier s = new Supplier(name, address);
		int supplierId = supplier.createSupplier(s);
		System.out.println("Controller - Supplier created: " + name);
		return supplierId;
	}
	
	/**
	 * Change product stock.
	 *
	 * @param qty the qty
	 */
	public void changeProductStock(int qty) {
		model.changeProductStock(productId, qty);
	}

	/**
	 * Adds the new product.
	 *
	 * @param name the name
	 * @param qty the qty
	 * @param price the price
	 */
	public int addNewProduct(String name, int qty, double price) {
		Products newProduct = new Products(name, qty, price);
		int productId = model.addNewProduct(newProduct);			
		return productId;
	}

	/**
	 * Gets the products.
	 *
	 * @return the products
	 */
	public Products[] getProducts() {
		return model.getProducts();
	}

	/**
	 * Gets the product.
	 *
	 * @return the product
	 */
	public Products getProduct() {
		return model.getProduct(productId);		
	}
	public Products getProduct(int id) {
		return model.getProduct(id);		
	}
	
	/**
	 * Delete product.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public boolean deleteProduct(int id) {
		return model.deleteProduct(id);
	}
	
	/**
	 * Sets the product id.
	 *
	 * @param productId the new product id
	 */
	public void setProductId(int productId) {
		this.productId = productId;
		
	}
	/**
	 * Method for updating the UI.
	 */
	public void update() {
		System.out.println("Controller - update");
		if(InventaariKontrolleri.getInstance() != null)
			InventaariKontrolleri.getInstance().refreshTable();
	}
	/**
	 * Change language to Finnish
	 */
	public void setFiLang() {
		lang = "fi";
	}
	/**
	 * Change language to English
	 */
	public void setEnLang() {
		lang = "en";
	}
	/**
	 * 
	 * @return language
	 */
	public String getLang() {
		return lang;
	}
}
