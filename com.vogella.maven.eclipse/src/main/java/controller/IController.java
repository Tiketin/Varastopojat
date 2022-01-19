package controller;



import model.Products;

public interface IController {
	public void changeQty(int qty);
	public void changeProductStock(int quantity);
	public Products[] getProducts();
	public Products getProduct();
	public int addNewProduct(String name, int qty, double price);
	public boolean deleteProduct(int id);
	public void setProductId(int id);
	public int createSupplier(String name, String address);
}
